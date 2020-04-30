package com.example.securoty02.controller;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    /**
     * 密码加密
     * Spring security 推荐使用 BCryptPasswordEncoder*/
    BCryptPasswordEncoder bCryptPasswordEncoder;
    PasswordEncoder passwordEncoder;
    @GetMapping("/hello")
    public String hello(){
        return "hello";
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
