package com.homejim.framework.context.init;

import com.homejim.framework.annotation.Table;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Set;

/**
 * 初始化类
 */
public class Initializer implements ApplicationContextInitializer {

    public void initialize(ConfigurableApplicationContext cxt) {
        ConfigurableEnvironment environment = cxt.getEnvironment();
        String scanPackage = environment.getProperty("scanPackage");
        if (scanPackage != null) {
            Reflections reflections = new Reflections(scanPackage);
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Table.class);
            for (Class<?> aClass : classes) {
                System.out.println(aClass.getName());
            }
        }
    }
}
