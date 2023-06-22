package com.seat.reservation.common.security;

import com.seat.reservation.common.cache.CustomRedisCacheWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.seat.reservation.common.cache.CustomRedisCacheWriter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomRedisCacheWriter redisCacheWriter;

    private static final String loginUrl = "/login";
    private static final String signUpUrl = "/user/signUp";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setFilterProcessesUrl(loginUrl);
        customAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());
        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler(redisCacheWriter);
    }

    @Bean
    public CustomLoginFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler();
    }

    @Bean
    public JwtFilter jwtFilter() {
        String[] allowUrl = {loginUrl, signUpUrl};
        return new JwtFilter(allowUrl);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .formLogin().disable()
            .authorizeRequests()
            .antMatchers(loginUrl, signUpUrl).permitAll()
            .anyRequest().authenticated()
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 일단 주석
            .and()
                .addFilterBefore(jwtFilter(), ExceptionTranslationFilter.class)
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
