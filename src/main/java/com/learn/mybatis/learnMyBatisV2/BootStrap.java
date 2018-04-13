package com.learn.mybatis.learnMyBatisV2;



import com.learn.mybatis.learnMyBatisV2.config.GpConfiguration;
import com.learn.mybatis.learnMyBatisV2.config.mappers.TestMapper;
import com.learn.mybatis.learnMyBatisV2.executor.ExecutorFactory;
import com.learn.mybatis.learnMyBatisV2.session.GpSqlSession;

import java.io.IOException;

/**
 */
public class BootStrap {
    public static void main(String[] args) throws IOException {
        start();
    }

    private static void start() throws IOException {
        GpConfiguration configuration = new GpConfiguration();
        configuration.setScanPath("com.learn.mybatis.learnMyBatisV2.config.mappers");
        configuration.build();
        GpSqlSession sqlSession = new GpSqlSession(configuration,
                ExecutorFactory.get(ExecutorFactory.ExecutorType.CACHING.name(),configuration));
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        long start = System.currentTimeMillis();
        Test test = testMapper.selectByPrimaryKey(1);
        System.out.println("cost:"+ (System.currentTimeMillis() -start));
    }
}
