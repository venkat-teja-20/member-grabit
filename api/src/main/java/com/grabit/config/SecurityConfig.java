package com.grabit.config;

import com.grabit.handler.AuthorizationHandler;
import com.grabit.handler.HandleAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final HandleAuthenticationEntryPoint authenticationEntryPoint;

    private final AuthorizationHandler authorizationHandler;

    private final JwtFilter jwtFilter;

    private static String[] PERMITTED_PATHS=new String[]{
            "/member/create",
            "/member/credentials",
            "/member/delete/*",
    };

    public SecurityConfig(HandleAuthenticationEntryPoint authenticationEntryPoint, AuthorizationHandler authorizationHandler, JwtFilter jwtFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authorizationHandler = authorizationHandler;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests->requests
                        .requestMatchers(PERMITTED_PATHS).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex->ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(authorizationHandler)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
