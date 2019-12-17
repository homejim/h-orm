package com.homejim.framework.test;

import com.homejim.framework.context.init.Initializer;
import com.homejim.framework.sql.SqlFactory;
import com.homejim.framework.sql.mapping.MappedStatement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@ComponentScan("com.homejim")
@Controller("/sample")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebApplication.class);
        application.addInitializers(new Initializer());
        application.run(args);

        MappedStatement sql = SqlFactory.getSql("mysql$com.homejim.framework.test.entity.user$select");
        System.out.println("22");
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "helloworld";
    }
}
