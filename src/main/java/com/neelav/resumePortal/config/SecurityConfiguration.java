package com.neelav.resumePortal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    //AUTHENTICATION
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        auth.userDetailsService(userDetailsService);
    }

    //AUTHORIZATION
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/edit").authenticated()
                .antMatchers("/*","static/css","static/js").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .and()
                .formLogin();
                /*.loginPage("/login")
                .loginProcessingUrl("/authenticate");*/

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }
}
