package com.cx.springboot.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author chenxi
 * @Package:
 * @Description:
 * @create 2021·12
 */

@Controller
public class HomeController{


    @RequestMapping( path = {"/","/index"},method = RequestMethod.GET)
    public String home(){
        return "/index";
    }


    @RequestMapping( path = {"/event"},method = RequestMethod.GET)
    public String event(){
        return "/site/event";
    }


}