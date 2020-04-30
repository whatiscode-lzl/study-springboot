package com.example.security03.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liaozhenglong
 * @Date 2020/4/30 15:09
 **/

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hell!";
    }

    @GetMapping("/admin/hello")
    public String admin(){
        return "hello 你拥有admin权限";
    }

    @GetMapping("/user/hello")
    public String user(){
        return "hello 你拥有user权限";
    }
}
