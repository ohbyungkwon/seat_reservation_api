package com.seat.reservation.common.security;

import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.domain.enums.CacheName;
import com.seat.reservation.common.repository.RefreshTokenStoreRepository;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.security.oauth.CustomOAuth2ClientUserService;
import com.seat.reservation.common.security.oauth.CustomOAuth2LoginFailureHandler;
import com.seat.reservation.common.security.oauth.CustomOAuth2LoginSuccessHandler;
import com.seat.reservation.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Map;

/**
 * Config file about Spring Security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${oauth2.login.success-callback-url}")
    private String oauthLoginSuccessCallbackUrl;

    @Value("${oauth2.login.failure-callback-url}")
    private String oauthLoginFailureCallbackUrl;

    @Autowired
    private Map<String, CustomRedisCache> redisCacheMap;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenStoreRepository refreshTokenStoreRepository;

    @Autowired
    private CustomOAuth2ClientUserService customOAuth2ClientUserService;

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
        customAuthenticationFilter.setFilterProcessesUrl(CommonUtil.allowUrls[0]);
        customAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());
        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler(redisCacheMap, refreshTokenStoreRepository);
    }

    @Bean
    public CustomOAuth2LoginSuccessHandler customOAuth2LoginSuccessHandler() {
        return new CustomOAuth2LoginSuccessHandler(redisCacheMap, refreshTokenStoreRepository,
                oauthLoginSuccessCallbackUrl);
    }

    @Bean
    public CustomLoginFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler(userRepository);
    }

    @Bean
    public CustomOAuth2LoginFailureHandler customOAuth2LoginFailureHandler() {
        return new CustomOAuth2LoginFailureHandler(oauthLoginFailureCallbackUrl);
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(
                redisCacheMap.get(CacheName.TOKEN_CACHE.getValue()),
                CommonUtil.allowUrls);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
            .formLogin().disable()
            .authorizeRequests()
            .antMatchers(CommonUtil.allowUrls).permitAll()
            .anyRequest().authenticated()
            .and()
                // Set for the JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilterAfter(jwtFilter(), ExceptionTranslationFilter.class)
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
            .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize") //클라이언트 첫 로그인 URI
            .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/code/**")
            .and()
                .userInfoEndpoint()
                .userService(customOAuth2ClientUserService)
            .and()
                .successHandler(customOAuth2LoginSuccessHandler())
                .failureHandler(customOAuth2LoginFailureHandler());
    }
}
