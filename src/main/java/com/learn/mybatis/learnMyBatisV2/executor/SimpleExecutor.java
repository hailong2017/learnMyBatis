package com.learn.mybatis.learnMyBatisV2.executor;


import com.learn.mybatis.learnMyBatisV2.config.GpConfiguration;
import com.learn.mybatis.learnMyBatisV2.config.MapperRegistory;
import com.learn.mybatis.learnMyBatisV2.statement.StatementHandler;

public class SimpleExecutor implements Executor {
    private GpConfiguration configuration;

    public SimpleExecutor(GpConfiguration configuration) {
        this.configuration = configuration;
    }

    public GpConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(GpConfiguration configuration) {
        this.configuration = configuration;
    }

    public <E> E query(MapperRegistory.MapperData mapperData, Object parameter)
            throws Exception {
            //初始化StatementHandler --> ParameterHandler --> ResultSetHandler
            StatementHandler handler = new StatementHandler(configuration);
            return (E) handler.query(mapperData, parameter);
    }
}