package com.wyp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author WYP
 * @date 2021-07-11 10:10
 */
@Controller
public class indexController {

    @RequestMapping("/index")
    public String toLogin(){
        //跳转到login页面
        return "login";
    }
}
