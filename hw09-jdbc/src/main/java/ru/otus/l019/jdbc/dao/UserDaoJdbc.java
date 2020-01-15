package ru.otus.l019.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l019.api.dao.UserDao;
import ru.otus.l019.api.dao.UserDaoException;
import ru.otus.l019.api.model.ObjectSerializer;
import ru.otus.l019.api.model.TableObject;
import ru.otus.l019.api.sessionmanager.SessionManager;
import ru.otus.l019.jdbc.DbExecutor;
import ru.otus.l019.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<Object> dbExecutor;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Object> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<Object> load(long id, Class clazz) {
        TableObject tableObject = ObjectSerializer.toTableClazz(clazz);
        List<TableObject.Column> columns = tableObject.getColumns();
        TableObject.Column idColumn = columns.get(0);
        TableObject.Column column1 = columns.get(1);
        TableObject.Column column2 = columns.get(2);
        try {
            return dbExecutor.selectRecord(getConnection(), tableObject.getSelect(), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        try {
                            return clazz.getDeclaredConstructor(long.class, String.class, long.class)
                                    .newInstance(resultSet.getLong(idColumn.getName()), resultSet.getString(column1.getName())
                                            , resultSet.getLong(column2.getName()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
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
        TableObject.Column column1 = columns.get(1);
        TableObject.Column column2 = columns.get(2);
        try {
            return dbExecutor.insertRecord(getConnection(), tableObject.getInsert(),
                    Arrays.asList(column1.getValue(), column2.getValue()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(Object o) {
        TableObject tableObject = ObjectSerializer.toTableObject(o);
        List<TableObject.Column> columns = tableObject.getColumns();
        TableObject.Column idColumn = columns.get(0);
        TableObject.Column column1 = columns.get(1);
        TableObject.Column column2 = columns.get(2);
        try {
            dbExecutor.updateRecord(getConnection(), tableObject.getUpdate(),
                    Arrays.asList(column1.getValue(), column2.getValue(), idColumn.getValue()));
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
}
