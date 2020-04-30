package com.example.securoty02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @Author liaozhenglong
 * @Date 2020/4/30 10:25
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AbstractRequestMatcherRegistry matcherRegistry; // 里面有提示Can't configure antMatchers after anyRequest

    @Bean
    PasswordEncoder passwordEncoder(){
        //暂时不加密
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("admin").roles("admin").build());
        manager.createUser(User.withUsername("user").password("user").roles("user").build());
        return manager;
    }
    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");  // 让上级拥有下级的权限
        return hierarchy;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()// 开启在内存中定义用户

                .withUser("admin")
                .password("admin").roles("admin")
                .and() //配置多个用户用and连接 and相当于XML的结束标签，使得下一个操作回到了.inMemoryAuthentication()之后
                .withUser("user")
                .password("user")
                .roles("user");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").hasAnyRole("user")
                .antMatchers("/admin/**").hasAnyRole("admin")
                .anyRequest().authenticated()
//                .antMatchers("/user/**").hasAnyRole("user")
//                .antMatchers("/admin/**").hasAnyRole("admin")   //  -----!!!!!!!!Can't configure antMatchers after anyRequest
                .and()
                .formLogin()
                .loginPage("/login.html")
                .permitAll()
                .and()
                .csrf().disable();
    }
}
