package org.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.Arrays;

/**
 * @author luis mora
 */
@SpringBootApplication
@ImportResource("classpath*:spring-webservices.xml")
public class App {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(App.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println("#####################" + beanName);
        }

    }
}

