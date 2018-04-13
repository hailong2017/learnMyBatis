package com.learn.mybatis.learnMyBatisV1;

/**
 * Created by hailong on 2018/4/13.
 */
public interface YaoExecutor {
     <T> T query(String statement, String parameter);}
