package EmployeeSystem.demo.controller;

import EmployeeSystem.demo.common.ApiResponse;
import EmployeeSystem.demo.model.User;
import EmployeeSystem.demo.model.dto.UserCreateDto;
import EmployeeSystem.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){

        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId){

        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserCreateDto userCreateDto){

        userService.addUser(userCreateDto);
        return new ResponseEntity<>(new ApiResponse(true, "User has been added"), HttpStatus.CREATED);
    }

}
