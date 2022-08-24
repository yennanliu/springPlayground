package com.yen.springMybatisDemo1.mapper;

// https://www.youtube.com/watch?v=D_DDE_o7ks0&list=PLmOn9nNkQxJEWFBs6hVmDC5m8SbbIiDwY&index=39
// https://www.youtube.com/watch?v=gk_pm_Uaa_Y&list=PLmOn9nNkQxJEWFBs6hVmDC5m8SbbIiDwY&index=42
// https://www.youtube.com/watch?v=gk_pm_Uaa_Y&list=PLmOn9nNkQxJEWFBs6hVmDC5m8SbbIiDwY&index=42

import com.yen.springMybatisDemo1.bean.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestEmpMapper {

    @Autowired
    EmpMapper empMapper;

    /** Deal with java bean attr - SQL col mismatch case
     *
     *    solution 1) : use alias in SQL query in mapper xml
     *    solution 2) : via mybatis conf (mybatis.configuration.map-underscore-to-camel-case = true)
     *    solution 3) : via resultMap : use user-own defined mapping
     *
     *      e.g. :
     *
     *       <resultMap id="empResultMap" type="com.yen.springMybatisDemo1.bean.Emp">
     *         <!--
     *             property : java bean attr name
     *             column : SQL table column name
     *         -->
     *         <id property="eid" column="eid"></id>  <!-- PK (primary key) -->
     *         <result property="empName" column="emp_name"></result> <!-- other normal attr -->
     *         <result property="age" column="age"></result>
     *         <result property="sex" column="sex"></result>
     *         <result property="email" column="email"></result>
     *         <result property="did" column="did"></result>
     *      </resultMap>
     */
    @Test
    public void test1(){

        List<Emp> res1 = empMapper.getAllEmp();
        System.out.println(">>> res1 = " + res1);
        res1.stream().forEach(System.out::println);
    }

    @Test
    public void test2(){

        List<Emp> res1 = empMapper.getAllEmp2();
        System.out.println(">>> res1 = " + res1);
        res1.stream().forEach(System.out::println);
    }

}
