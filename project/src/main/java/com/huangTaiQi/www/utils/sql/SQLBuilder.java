package com.huangTaiQi.www.utils.sql;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 14629
 */
public class SQLBuilder {

    private final String tableName;
    private final List<String> columns = new ArrayList<>();
    private final List<String> whereClause = new ArrayList<>();
    private final List<String> joinClause = new ArrayList<>();
    private final List<String> groupByClause = new ArrayList<>();
    private final List<String> havingClause = new ArrayList<>();
    private final List<String> orderByClause=new ArrayList<>();
    private int limit;
    private int offset;


    public SQLBuilder(String tableName) {
        this.tableName = tableName;
    }

    public SQLBuilder insert(String column) {
        columns.add(column);
        whereClause.add("?");
        return this;
    }
    public SQLBuilder update(String column) {
        columns.add(column);
        return this;
    }
    public SQLBuilder select(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }
    public SQLBuilder count(String column) {
        columns.add("count("+column+")");
        return this;
    }

    public SQLBuilder where(String condition) {
        whereClause.add(condition+"= ? ");
        return this;
    }
    public SQLBuilder blurWhere(String condition) {
        whereClause.add(condition+" LIKE  ? ");
        return this;
    }

    public SQLBuilder orderBy(String orderByClause) {
        this.orderByClause.add(orderByClause) ;
        return this;
    }
    public SQLBuilder having(String condition) {
        this.havingClause.add(condition) ;
        return this;
    }

    public SQLBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public SQLBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }
    public SQLBuilder groupBy(String column) {
        columns.add(column);
        return this;
    }

    public SQLBuilder join(String table, String... condition) {
        joinClause.add(" JOIN " + table + " ON " + String.join(" AND ", condition));
        return this;
    }

    public String buildInsert() {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName);
        sql.append("(").append(String.join(",", columns)).append(")");
        sql.append(" VALUES (").append(String.join(",", whereClause)).append(")");
        return sql.toString();
    }

    public String buildSelect() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(String.join(",", columns)).append(" FROM ").append(tableName);
        if(!joinClause.isEmpty()){
            sql.append(String.join(" ",joinClause));
        }
        if (!whereClause.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", whereClause));
        }
        if(!groupByClause.isEmpty()){
            sql.append(" GROUP BY ").append(String.join(",",groupByClause));
        }
        if (!havingClause.isEmpty()) {
            sql.append(" HAVING ").append(String.join(" AND ",orderByClause));
        }
        if (!orderByClause.isEmpty()) {
            sql.append(" ORDER BY ").append(String.join(",",orderByClause));
        }
        if (limit > 0) {
            sql.append(" LIMIT ").append(limit);
        }
        if (offset > 0) {
            sql.append(" OFFSET ").append(offset);
        }
        return sql.toString();
    }

    public String buildUpdate() {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName).append(" SET ");
        for (int i = 0; i < columns.size(); i++) {
            sql.append(columns.get(i)).append(" = ?");
            if (i != columns.size() - 1) {
                sql.append(",");
            }
        }
        if (!whereClause.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", whereClause));
        }
        return sql.toString();
    }

    public String buildDelete() {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(tableName);
        if (!whereClause.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", whereClause));
        }
        return sql.toString();
    }


}
