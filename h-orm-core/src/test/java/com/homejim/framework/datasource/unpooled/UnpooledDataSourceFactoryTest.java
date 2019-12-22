package com.homejim.framework.datasource.unpooled;

import org.junit.Test;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import static org.junit.Assert.*;

public class UnpooledDataSourceFactoryTest {


    @Test
    public void shouldNotRegisterTheSameDriverMultipleTimes() throws Exception {
        // https://code.google.com/p/mybatis/issues/detail?id=430
        UnpooledDataSource dataSource = null;
        dataSource = new UnpooledDataSource("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:multipledrivers", "sa", "");
        dataSource.getConnection().close();
        int before = countRegisteredDrivers();
        dataSource = new UnpooledDataSource("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:multipledrivers", "sa", "");
        dataSource.getConnection().close();
        assertEquals(before, countRegisteredDrivers());
    }

    protected int countRegisteredDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        int count = 0;
        while (drivers.hasMoreElements()) {
            drivers.nextElement();
            count++;
        }
        return count;
    }
}