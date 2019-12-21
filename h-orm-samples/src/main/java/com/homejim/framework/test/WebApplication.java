package com.homejim.framework.test;

import com.alibaba.fastjson.JSON;
import com.homejim.framework.context.init.Initializer;
import com.homejim.framework.session.HDao;
import com.homejim.framework.sql.SqlPool;
import com.homejim.framework.sql.mapping.MappedStatement;
import com.homejim.framework.test.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "helloworld";
    }
}
