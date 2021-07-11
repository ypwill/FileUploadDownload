package com.wyp.controller;

import com.wyp.entity.User;
import com.wyp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


/**
 * @author WYP
 * @date 2021-07-11 10:02
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public String login(User user, HttpSession session){
        User userDB = userService.login(user);
        if(userDB !=null){
            session.setAttribute("user",userDB);
            return "redirect:/file/showAll";
        }else{
            return "redirect:/index";
        }
    }



}
