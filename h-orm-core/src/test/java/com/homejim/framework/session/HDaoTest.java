package com.homejim.framework.session;

import com.alibaba.fastjson.JSON;
import com.homejim.framework.context.init.Initializer;
import com.homejim.framework.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author homejim
 * @description 测试
 * @create: 2019-12-21 12:20
 */
@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest
public class HDaoTest {
    @Autowired
    private ConfigurableEnvironment environment;

    @Before
    public void init() {
        SpringApplication application = new SpringApplication(HDaoTest.class);
        application.addInitializers(new Initializer());
        application.run("");
    }

    @Test
    public void test() {
        HDao hDao = new HDao();
        User user = hDao.selectById(User.class, "123");
        String s = JSON.toJSONString(user);
        System.out.println(s);
    }

}
