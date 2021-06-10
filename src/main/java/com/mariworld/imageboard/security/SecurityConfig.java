package com.mariworld.imageboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeinedHandler();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http/*.authorizeRequests()
                .antMatchers("/board/register").hasAnyRole("ROLE_ADMIN")
                .antMatchers("/board/**").authenticated()

                .and()*/
                .formLogin().loginPage("/customLogin").loginProcessingUrl("/login")

                .and()
                .logout().logoutUrl("/customLogout").invalidateHttpSession(true)

                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }
}
