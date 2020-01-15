package ru.otus.l019.api.model;

import java.util.List;

public class TableObject {

    String tableName;
    List<Column> columns;

    public TableObject(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getInsert() {
        TableObject.Column column1 = columns.get(1);
        TableObject.Column column2 = columns.get(2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into ");
        stringBuilder.append(tableName);
        stringBuilder.append("(");
        stringBuilder.append(column1.getName());
        stringBuilder.append(",");
        stringBuilder.append(column2.getName());
        stringBuilder.append(") values (?,?)");
        return stringBuilder.toString();
    }

    public String getSelect() {
        TableObject.Column idColumn = columns.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ");
        for (int i = 0; i < columns.size(); i++) {
            stringBuilder.append(columns.get(i).getName());
            if (i < columns.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(" from ");
        stringBuilder.append(tableName);
        stringBuilder.append(" where ");
        stringBuilder.append(idColumn.getName());
        stringBuilder.append(" = ?");
        return stringBuilder.toString();
    }

    public String getUpdate() {
        TableObject.Column idColumn = columns.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE ");
        stringBuilder.append(tableName);
        stringBuilder.append(" SET ");
        for (int i = 1; i < columns.size(); i++) {
            stringBuilder.append(columns.get(i).getName());
            stringBuilder.append(" = ");
            stringBuilder.append("?");
            if (i < columns.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(" WHERE ");
        stringBuilder.append(idColumn.getName());
        stringBuilder.append(" = ?");
        return stringBuilder.toString();
    }

    public static class Column {
        boolean id;
        String name;
        String value;

        public Column(boolean id, String name, String value) {
            this.id = id;
            this.name = name;
            this.value = value;
        }

        public boolean isId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
}
