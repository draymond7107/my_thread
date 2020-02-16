package com.draymond.spring;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ZhangSuchao
 * @create 2019/8/26
 * @since 1.0.0
 */

public class MainTest {
    public static void main(String[] args) {
        //xml配置
        //返回ioc容器
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
//        Persion persion = (Persion) classPathXmlApplicationContext.getBean("persion");
//        System.out.println(persion.toString());

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        Persion persion1 = (Persion) context.getBean("persion");
        Persion persion2 = (Persion) context.getBean("persion");
        System.out.println(persion1==persion2);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        System.out.println(persion1.toString());
        System.out.println(beanDefinitionNames);

    }
}
