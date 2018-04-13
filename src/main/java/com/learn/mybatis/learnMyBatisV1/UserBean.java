package com.learn.mybatis.learnMyBatisV1;


import lombok.Data;

/**
 * Created by hailong on 2018/4/13.
 */
//不用写getset
@Data
public class UserBean {
    private Integer id;

    private Integer nums;

    private String name;

    public UserBean(Integer id, Integer nums, String name) {
        this.id = id;
        this.nums = nums;
        this.name = name;
    }

    public UserBean() {

    }
}
