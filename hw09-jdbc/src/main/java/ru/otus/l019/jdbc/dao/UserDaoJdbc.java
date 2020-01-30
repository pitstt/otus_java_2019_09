package ru.otus.l019.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l019.api.dao.UserDao;
import ru.otus.l019.api.dao.UserDaoException;
import ru.otus.l019.api.model.ObjectSerializer;
import ru.otus.l019.api.model.TableObject;
import ru.otus.l019.api.service.DbServiceException;
import ru.otus.l019.api.sessionmanager.SessionManager;
import ru.otus.l019.jdbc.DbExecutor;
import ru.otus.l019.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class UserDaoJdbc<T> implements UserDao<T> {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<Object> dbExecutor;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Object> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<T> load(long id, Class clazz) {
        TableObject tableObject = ObjectSerializer.toTableObject(clazz);
        try {
            return (Optional<T>) dbExecutor.selectRecord(getConnection(), tableObject.getSelect(), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        try {
                            Object o = clazz.getDeclaredConstructor()
                                    .newInstance();
                            List<Field> fields = Arrays.asList(o.getClass().getDeclaredFields());
                            for (int i = 0; i < fields.size(); i++) {
                                Field f = fields.get(i);
                                f.setAccessible(true);
                                setField(o, f.getName(), resultSet.getString(f.getName()));
                            }
                            return o;
                        } catch (DbServiceException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } catch (SQLException e) {
                    throw new DbServiceException(e);
                }
                return null;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Override
    public long create(Object o) {
        TableObject tableObject = ObjectSerializer.toTableObject(o);
        List<TableObject.Column> columns = tableObject.getColumns();
        List<String> stringList = columns.stream().filter(column -> !column.isId()).map(TableObject.Column::getValue).collect(Collectors.toList());
        try {
            return dbExecutor.insertRecord(getConnection(), tableObject.getInsert(),
                    stringList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(Object o) {
        TableObject tableObject = ObjectSerializer.toTableObject(o);
        List<TableObject.Column> columns = tableObject.getColumns();
        TableObject.Column idColumn = columns.stream().filter(column -> column.isId()).findFirst().orElse(null);
        List<String> resultList = columns.stream().filter(column -> !column.isId()).map(TableObject.Column::getValue)
                .collect(Collectors.toList());
        assert idColumn != null;
        resultList.add(idColumn.getValue());
        try {
            dbExecutor.updateRecord(getConnection(), tableObject.getUpdate(),
                    resultList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    public static boolean setField(Object targetObject, String fieldName, String fieldValue) {
        Field field;
        try {
            field = targetObject.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        Class superClass = targetObject.getClass().getSuperclass();
        while (field == null && superClass != null) {
            try {
                field = superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
            }
        }
        if (field == null) {
            return false;
        }
        field.setAccessible(true);
        try {
            if (field.get(targetObject) instanceof Integer) {
                field.set(field, Integer.valueOf(fieldValue));
            } else if (field.get(targetObject) instanceof String) {
                field.set(targetObject, fieldValue);
            } else if (field.get(targetObject) instanceof BigInteger) {
                field.set(targetObject, BigInteger.valueOf(Long.parseLong(fieldValue)));
            } else if (field.get(targetObject) instanceof BigDecimal) {
                field.set(targetObject, BigDecimal.valueOf(Double.parseDouble(fieldValue)));
            } else if (field.get(targetObject) instanceof Double) {
                field.set(targetObject, Double.parseDouble(fieldValue));
            } else if (field.get(targetObject) instanceof Character) {
                field.set(targetObject, fieldValue);
            } else if (targetObject instanceof Byte) {
                field.set(field.get(targetObject), Byte.valueOf(fieldValue));
            } else if (field.get(targetObject) instanceof Long) {
                field.set(targetObject, Long.parseLong(fieldValue));
            } else if (field.get(targetObject) instanceof Float) {
                field.set(targetObject, Float.valueOf(fieldValue));
            }
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
