package com.example.colorbase.security;


import com.example.colorbase.repos.users.UserRepo;
import com.example.colorbase.services.users.UserLoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    private final UserRepo userRepo;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authProvider())
                .authorizeRequests()
                .antMatchers(
                        "/collections",
                        "/settings"
                ).authenticated()

                .anyRequest().permitAll()
                .and()
                .addFilterBefore(customLoginFilter(), LoginFilter.class)
                .formLogin().permitAll()
                .loginPage("/login")
                .failureUrl("/error/no_access.html")
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable().cors();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserLoginService(userRepo);
    }


    @Bean
    @SneakyThrows
    @Override
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    private LoginFilter customLoginFilter() {
        final LoginFilter loginFilter =  new LoginFilter(authenticationManagerBean(), objectMapper);
        loginFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", POST.name()));

        return loginFilter;
    }




}

