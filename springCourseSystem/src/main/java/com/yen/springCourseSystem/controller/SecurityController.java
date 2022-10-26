package com.yen.springCourseSystem.controller;

// book p. 257

import com.yen.springCourseSystem.bean.User;
import com.yen.springCourseSystem.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping("/security")
@Log4j2
public class SecurityController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String root(){
        return "index";
    }

    @GetMapping("/toLogin")
    public String toLogin(Map<String, Object> map){
        map.put("user", new User());
        return "login";
    }

    // register page
    @RequestMapping("/register")
    public String register(){

        return "register";
    }

    // register doc page
    @RequestMapping("/readdoc")
    public String readdoc(){

        return "readdoc";
    }

    // register method
    @RequestMapping("addregister")
    public String register(HttpServletRequest request){

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        log.info(">>> username = {}, password = {}, password2 = {}", username, password, password2);

        if (password.equals(password2)){
            User userEntity = new User();
            userEntity.setUserName(username);
            userEntity.setUserPwd(password);
            userService.addUser(userEntity);
            log.info(">>> login OK");
            return "login";
            //return "test";
        }else{
            log.info(">>> login failed, plz try again");
            return "register";
        }
    }

    @GetMapping("mainController")
    public String main(Model model){ // https://youtu.be/nKFM5S1rhJo?t=315

        log.info(">>> mainController");
        HashMap<String, Object> user = new HashMap<>();
        user.put("name", "admin");
        model.addAttribute("user", user);
        return "main";
    }

    // NOTE !! we use redirect here
    @GetMapping("/logout")
    public String logout(){

        log.info(">>> logout");
        return "redirect:/security/toLogin";
    }

}
