package com.example.securoty01.controller;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liaozhenglong
 * @Date 2020/4/30 9:44
 **/

@RestController
public class HelloController {

    /**
     * 可以看看这两个类的细节*/
    UserDetailsServiceAutoConfiguration userDetailsServiceAutoConfiguration;
    SecurityProperties securityProperties;
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
