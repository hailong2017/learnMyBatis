package com.learn.mybatis.learnMyBatisV1;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hailong on 2018/4/13.
 */
public class YaoConfiguration {
    public <T> T getMapper(Class<T> clazz,YaoSqlsession sqlsession){
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{clazz},
                new YaoMapperProxy(sqlsession));
    }


    /**
     * xml解析好了
     */
    static class TestMapperXml{
        public static final String namespace = "com.learn.mybatis.learnMyBatisV1.YaoMapper";

        public static final Map<String,String> methodSqlMapping = new HashMap<>();

        static {
            methodSqlMapping.put("selectByPrimaryKey","select * from test where id =%d");
        }
    }

}
