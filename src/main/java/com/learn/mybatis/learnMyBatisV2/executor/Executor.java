package com.learn.mybatis.learnMyBatisV2.executor;


import com.learn.mybatis.learnMyBatisV2.config.MapperRegistory;

/**
 */
public interface Executor {

    <T> T query(MapperRegistory.MapperData mapperData, Object parameter) throws Exception;
}
