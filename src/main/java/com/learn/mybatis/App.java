package com.learn.mybatis;

import com.learn.mybatis.learnMyBatisV1.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        YaoSqlsession sqlsession = new YaoSqlsession(new YaoConfiguration(),new YaoSimpleExecutor());
        YaoMapper mapper = sqlsession.getMapper(YaoMapper.class);
        UserBean test = mapper.selectByPrimaryKey(1);
        System.out.println(test);
    }
}
