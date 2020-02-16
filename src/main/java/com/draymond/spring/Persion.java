package com.draymond.spring;


import lombok.Data;

/**
 * @author ZhangSuchao
 * @create 2019/8/26
 * @since 1.0.0
 */

@Data
public class Persion {
    private Integer age;
    private String name;

    public Persion(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Persion() {

    }
}
