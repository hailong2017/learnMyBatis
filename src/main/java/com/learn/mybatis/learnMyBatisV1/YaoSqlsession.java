package com.learn.mybatis.learnMyBatisV1;

/**
 * Created by hailong on 2018/4/13.
 */
//mybatis管理类，对外开放
public class YaoSqlsession {
    //获取mapper
    private YaoConfiguration configuration;
    //执行和handler处理
    private YaoExecutor  execcutor;

    public YaoSqlsession(YaoConfiguration configuration, YaoExecutor execcutor) {
        this.configuration = configuration;
        this.execcutor = execcutor;
    }

    public <T> T getMapper(Class<T> clazz){
        return  configuration.getMapper(clazz,this);
    }

    public <T>T selectOne(String statement,String parameter){
        return execcutor.query(statement,parameter);
    }
}
