package com.draymond.spring;


import com.draymond.queue.LinkedBlockingQueueDemo;
import org.springframework.context.annotation.*;

/**
 * 使用注解的方式配置
 *
 * @author ZhangSuchao
 * @create 2019/8/26
 * @since 1.0.0
 */
//FilterType.ANNOTATION 使用注解策略
//FilterType.ASSIGNABLE_TYPE 按照指定的类型


@Configuration
//@ComponentScan(value = "com.draymond.queue", useDefaultFilters = false, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {LinkedBlockingQueueDemo.class})})
//@ComponentScans()
@ComponentScan(value = "com.draymond.spring")
public class MainConfig {

    //给容器注册一个bean,将bean放到spring ioc容器管理,类似包扫描
    //类型是方法返回值，id是方法名
    @Scope("prototype")     //  prototype,多实例   singletype,单实例（默认，ioc默认实例一个对象）

    @Bean(value = "persion")
    public Persion getPersion() {
        return new Persion(18, "里斯");
    }
}
