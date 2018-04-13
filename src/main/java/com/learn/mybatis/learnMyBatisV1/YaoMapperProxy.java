package com.learn.mybatis.learnMyBatisV1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by hailong on 2018/4/13.
 */
//mapper代理类
public class YaoMapperProxy implements InvocationHandler {
    private YaoSqlsession sqlsession;
    public YaoMapperProxy(YaoSqlsession sqlsession){
        this.sqlsession = sqlsession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getDeclaringClass().getName().equals(YaoConfiguration.TestMapperXml.namespace)){
            String sql = YaoConfiguration.TestMapperXml.methodSqlMapping.get(method.getName());
            return sqlsession.selectOne(sql,String.valueOf(args[0]));
        }
        return method.invoke(this,args);
    }
}
