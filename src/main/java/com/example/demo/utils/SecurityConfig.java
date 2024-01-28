package com.example.demo.utils;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	return http.csrf().disable() // why csrf disable : spring boot use not Session cookie but Local starage 
        .authorizeRequests() // begin authorize on requests
        .antMatchers("/board/**").authenticated() // add url to authorize
        .antMatchers("admin/**").access("hasRoll('ROLE_ADMIN')")
        .anyRequest().permitAll() // permit another requests all
        .and()
        .formLogin()
        .loginPage("/user/login")
        .loginProcessingUrl("/user/loginProc")
        .defaultSuccessUrl("/")
        .and()
        .logout()
        //.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        .logoutUrl("/user/logout")
        .logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true)
        .and().build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}