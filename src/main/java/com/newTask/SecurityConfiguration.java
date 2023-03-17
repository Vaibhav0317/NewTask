package com.newTask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser("abc")
                .password("abc")
                .roles("Seller")
                .and()
                .withUser("buyer")
                .password("1234")
                .roles("Buyer")
                .and()
                .withUser("Seller")
                .password("1234")
                .roles("Seller")
                .and()
                .withUser("xyz")
                .password("123")
                .roles("Buyer");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }

      @Override
      public void configure(HttpSecurity http) throws Exception {
          http.authorizeHttpRequests()
                  .antMatchers(HttpMethod.GET, "/showProducts").hasRole("Buyer")
                  .antMatchers(HttpMethod.POST, "/buy").hasRole("Buyer")
                  .antMatchers(HttpMethod.GET, "/showPurches").hasRole("Buyer")
                  .antMatchers(HttpMethod.POST, "/add").hasRole("Seller")
                  .antMatchers(HttpMethod.DELETE, "/delete/{id}").hasRole("Seller")
                  .antMatchers(HttpMethod.DELETE, "/deleteAll").hasRole("Seller")
                  .antMatchers(HttpMethod.PUT, "/update/{id}").hasRole("Seller")
                  .antMatchers("/**").hasRole("Seller")
                  .and()
                  .httpBasic()
                  .and()
                  .formLogin().permitAll()
                  .and()
                  .logout().permitAll()
                  .and()
                  .csrf().disable();
      }


}
