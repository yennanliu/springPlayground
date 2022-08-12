package com.yen.springMybatisDemo1.mapper;

// https://www.youtube.com/watch?v=_5a7CjR-XSw&list=PLmOn9nNkQxJEWFBs6hVmDC5m8SbbIiDwY&index=22
// https://www.youtube.com/watch?v=eesVBIuTC1k&list=PLmOn9nNkQxJEWFBs6hVmDC5m8SbbIiDwY&index=23

import com.yen.springMybatisDemo1.bean.MyUser;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.util.List;

/**
 *    Mybatis has 2 ways get parameter (to SQL)
 *
 *       1) ${} : concatenation of strings (字符串拼接)
 *              -> has SQL injection issue
 *              -> have to use this in some scenario
 *
 *       2) #{} : replacement (佔位符賦值)
 *              -> prefer this
 *              -> has NO SQL injection issue
 */

@SpringBootTest
public class TestParameterMapper {

    @Autowired
    ParameterMapper parameterMapper;

    @Test
    public void test1(){

        List<MyUser> user_list = parameterMapper.getAllUser();
        user_list.forEach(System.out::println);
    }

    /** review : tradition JDBC op */
    @Test
    public void testJDBC() throws Exception {

        String url = "jdbc:mysql://localhost:3306/mybatis";
        String user = "root";
        String password = "";

        String sql1 = "SELECT * FROM my_user WHERE id = ?";
        String sql2 = "SELECT * FROM my_user WHERE id = {}";

        Class.forName("");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement ps = connection.prepareStatement(sql1);
        ps.setString(1, "1");
    }

}
