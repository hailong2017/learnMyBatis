package com.learn.mybatis.learnMyBatisV2.config.mappers;


import com.learn.mybatis.learnMyBatisV2.Test;

public interface TestMapper { //com.gupaoedu.mybatis.gp.config.mappers.TestMapper
    Test selectByPrimaryKey(Integer userId);
}