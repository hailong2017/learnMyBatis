package com.learn.mybatis.learnMyBatisV2.mapper;


import com.learn.mybatis.learnMyBatisV2.config.MapperRegistory;
import com.learn.mybatis.learnMyBatisV2.session.GpSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 */
public class MapperProxy<T> implements InvocationHandler {
    private final GpSqlSession sqlSession;
    private final Class<T> mappperInterface;

    public MapperProxy(GpSqlSession gpSqlSession, Class<T> clazz) {
        this.sqlSession = gpSqlSession;
        this.mappperInterface = clazz;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperRegistory.MapperData mapperData =
                sqlSession.getConfiguration()
                        .getMapperRegistory()
                        .get(method.getDeclaringClass().getName() + "." + method.getName());
        if (null != mapperData) {
            System.out.println(String.format("SQL [ %s ], parameter [%s] ", mapperData.getSql(), args[0]));
            return sqlSession.selectOne(mapperData, String.valueOf(args[0]));
        }
        return method.invoke(proxy, args);
    }
}
