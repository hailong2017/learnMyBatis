package com.learn.mybatis.learnMyBatisV2.annotations;

import java.lang.annotation.*;

/**
 * Created by hailong on 2018/4/13.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    String value();
}
