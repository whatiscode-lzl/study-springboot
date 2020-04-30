package com.example.security03.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.io.PrintWriter;

/**
 * @Author liaozhenglong
 * @Date 2020/4/30 15:11
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    @Bean
    protected UserDetailsService userDetailsService(){
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        if (!manager.userExists("userhello")){
            manager.createUser(User.withUsername("userhello").password("123456").roles("user").build());
        }
        if (!manager.userExists("江南一客")){
            manager.createUser(User.withUsername("江南一客").password("123456").roles("admin").build());
        }
        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").hasRole("user")
                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/doLogin")
                .successHandler((req,resp,authentication) ->{
                    Object principal = authentication.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler((req,resp,e) ->{
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(e.getMessage());
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("注销成功");
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling()
                .authenticationEntryPoint((req, resp, authException) -> {
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.write("尚未登录，请先登录");
                            out.flush();
                            out.close();
                        }
                );

        /**
         * 使用postman:http://localhost:8080/doLogin
         * 请求体body的格式为：x-www-form-urlencoded*/
    }

}
