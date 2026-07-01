package com.anshul.resumeanalyzer.config;

import com.anshul.resumeanalyzer.security.CustomUserDetailsService;
import com.anshul.resumeanalyzer.service.CustomOAuth2UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/home",
                                "/register",
                                "/login",
                                "/forgot-password",
                                "/verify-otp",
                                "/reset-password",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        .anyRequest().authenticated()

                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .loginProcessingUrl("/login")

                        .defaultSuccessUrl("/dashboard", true)

                        .failureUrl("/login?error")

                        .permitAll()

                )

                .oauth2Login(oauth -> oauth

                        .loginPage("/login")

                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )

                        .defaultSuccessUrl("/dashboard", true)

                )

                .rememberMe(remember -> remember

                        .key("ResumeAnalyzerSecretKey")

                        .rememberMeParameter("remember-me")

                        .tokenValiditySeconds(60 * 60 * 24 * 30)
                        .userDetailsService(customUserDetailsService)

                )

                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl("/login?logout")

                        .deleteCookies("JSESSIONID", "remember-me")

                        .invalidateHttpSession(true)

                        .clearAuthentication(true)

                        .permitAll()

                );

        return http.build();
    }
}
