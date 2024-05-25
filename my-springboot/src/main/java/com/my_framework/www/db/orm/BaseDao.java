package com.my_framework.www.db.orm;

import com.my_framework.www.core.context.ResourceHolder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangtaiqi
 */
@Slf4j
public class BaseDao {
    private final Connection connection;

    public BaseDao(Connection connection) {
        this.connection = connection;
        if(sqlPrint==null){
            sqlPrint = Boolean.valueOf(ResourceHolder.getResourceLoader().getValue("sql.print"));
        }
    }

    private static Boolean sqlPrint= null;

    /**
     *
     * @param sql sql
     * @param cls 返回值的class
     * @param params sql的参数 ,可以为空
     * @return sql查询的所有结果
     * @throws Exception 让调用者知道有异常
     */
    public <T> List<T> selectByParams(String sql, Class<T> cls, Object... params) throws Exception {
        List<T> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //填sql
        for(int i=1;i<=params.length;i++){
            preparedStatement.setObject(i,params[i-1]);
        }
        if(sqlPrint){
            log.info(sql);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        //数据库数据的列数
        int columnCount = metaData.getColumnCount();
        while(resultSet.next()){
            //select count(*)
            //这里obj不能直接转化为int，运行会报错
            if(cls==Integer.class||cls==int.class){
                Object object = resultSet.getObject(1);
                Long l= (Long) object;
                int ii = l.intValue();
                list.add(cls.cast(ii));
                return list;
            }
            T t=cls.newInstance();
            for(int i=1;i<=columnCount;i++){
                //现在要往t里填参数
                //列名Label可以获取别名
                String columnLabel = metaData.getColumnLabel(i);
                Object resultSetObject = resultSet.getObject(i);
                String name = getFieldName(columnLabel);
                String relMethodName = "set"+name;
                String columnClassName = metaData.getColumnClassName(i);
                Class<?> columnType = Class.forName(columnClassName);
                Method method2 = cls.getMethod(relMethodName, columnType);
                method2.invoke(t, resultSetObject);
            }
            list.add(t);
        }
        return list.size()==0?null:list;
    }


    public <T> T selectOne(String sql, Class<T> cls, Object... params) throws Exception {
        List<T> list = this.selectByParams(sql, cls, params);
        if(list==null||list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }
    private String getFieldName(String name){
        StringBuilder builder=new StringBuilder();
        char[] chars = name.toCharArray();
        chars[0]-=32;
        for(int i=0;i<chars.length;i++){
            if(chars[i]=='_'){
                chars[i+1]-=32;
                i++;
            }
            builder.append(chars[i]);
        }
        return builder.toString();
    }

    /**
     * @param sql 修改的sql
     * @param args 参数
     */
    public int updateCommon(String sql, Object ...args) throws SQLException {
        PreparedStatement ps = null;
        ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }
        if(sqlPrint){
            log.info(sql);
        }
//        ps.execute();
        return ps.executeUpdate();
    }

//    /**
//     * 获取刚刚insert的主键值
//     * @return 插入数据的主键值
//     * @throws Exception 异常
//     */
//    public Integer getLastInsertId() throws Exception {
//        return selectOne("SELECT CAST(LAST_INSERT_ID() AS UNSIGNED) AS id", LastId.class).id.intValue();
//    }
}
