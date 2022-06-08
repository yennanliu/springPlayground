package com.yen.springBootAdvance1.controller;

// https://www.youtube.com/watch?v=Un_YC0fBKls&list=PLmOn9nNkQxJESDPnrV6v_aiFgsehwLgku&index=4
// https://www.youtube.com/watch?v=gfNx_iT6QpE&list=PLmOn9nNkQxJESDPnrV6v_aiFgsehwLgku&index=7
// https://www.youtube.com/watch?v=eIZxMWXEPmA&list=PLmOn9nNkQxJESDPnrV6v_aiFgsehwLgku&index=8
// https://www.youtube.com/watch?v=oFjcnwkZA3A&list=PLmOn9nNkQxJESDPnrV6v_aiFgsehwLgku&index=10

import com.yen.springBootAdvance1.bean.Employee;
import com.yen.springBootAdvance1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id){
        Employee emp = employeeService.getEmp(id);
        return emp;
    }

    @GetMapping("/emp")
    public Employee update(Employee employee){
        Employee emp = employeeService.updateEmp(employee);
        return emp;
    }

    @GetMapping("/delemp")
    public String delete(Integer id){
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/lastname/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName){
        return employeeService.getEmpByLastName(lastName);
    }

}
