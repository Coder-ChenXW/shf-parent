package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.PasswordAuthentication;


@Configuration
@EnableWebSecurity // 自动配置
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    // 在内存中设置一个认证的用户名密码
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("");
    }

    // 创建一个密码加密器放到ioc容器里
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 必须调用父类方法，否则认证失效,除非子类方法配置了认证和授权
//        super.configure(http);
        // 配置允许
        http.headers().frameOptions().sameOrigin();
        // 配置可以匿名访问的资源
        http.authorizeRequests().antMatchers("/static/**","/login").permitAll().anyRequest().authenticated();
        // 自定义登录页面
        http.formLogin().loginPage("/login") // 配置去自定义页面访问的路径
                .defaultSuccessUrl("/"); //配置登录成功后去往的地址
    }
}
