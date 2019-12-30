package com.homejim.framework.session;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.homejim.framework.context.init.Initializer;
import com.homejim.framework.datasource.pooled.PooledDataSourceFactory;
import com.homejim.framework.datasource.unpooled.UnpooledDataSourceFactory;
import com.homejim.framework.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

/**
 * @author homejim
 * @description 测试
 * @create: 2019-12-21 12:20
 */
@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest
public class HDaoTest {

    private HDao hDao = new HDao();

    @Before
    public void init() {
        SpringApplication application = new SpringApplication(HDaoTest.class);
        application.addInitializers(new Initializer());
        application.run("");
        UnpooledDataSourceFactory unpooledDataSourceFactory = new UnpooledDataSourceFactory();
        Properties properties = new Properties();
        properties.put("driver", "com.mysql.jdbc.Driver");
        properties.put("url", "jdbc:mysql://localhost:3306/horm_sample");
        properties.put("username", "root");
        properties.put("password", "");
        unpooledDataSourceFactory.setProperties(properties);

        hDao.setDataSourceFactory(unpooledDataSourceFactory);
    }

    @Test
    public void test() {

        User user = hDao.select(User.class, "123");
        String s = JSON.toJSONString(user);
        System.out.println(s);
        User user2 = hDao.select(User.class, "123");
        String s2 = JSON.toJSONString(user2);
        System.out.println(s2);
        user.setName("lalala");
        hDao.update(user);
    }

    @Test
    public void testDelete() {
        hDao.delete(User.class, "666");
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setId(IdUtil.simpleUUID());
        user.setName("hebiwen");
        hDao.insert(user);
    }

    @Test
    public void testPooledDatasource() {
        HDao hDao = new HDao();
        PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
        Properties properties = new Properties();
        properties.put("driver", "com.mysql.jdbc.Driver");
        properties.put("url", "jdbc:mysql://localhost:3306/horm_sample");
        properties.put("username", "root");
        properties.put("password", "123456");
        pooledDataSourceFactory.setProperties(properties);

        hDao.setDataSourceFactory(pooledDataSourceFactory);
        User user = hDao.select(User.class, "123");
        String s = JSON.toJSONString(user);
        System.out.println(s);
        User user2 = hDao.select(User.class, "123");
        String s2 = JSON.toJSONString(user2);
        System.out.println(s2);

    }

}
