package com.example.demo.business.config;

import com.example.demo.business.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private SSUserDetailsService userDetailService;
    @Autowired
    private UserRepository appUserRepository;
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(appUserRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
//                .authorizeRequests().anyRequest().authenticated()
//                .and().formLogin().loginPage("/login").permitAll();

                .authorizeRequests()
                .antMatchers( "/","/h2/**",  "/register", "/user/**",
                        "/detail/{id}","/css/**", "/js/**").permitAll()

//                .access("hasAnyAuthority('USER','ADMIN')")
//                .antMatchers("/admin").access("hasAuthority('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .httpBasic();
        http
                .csrf().disable();
        http
                .headers().frameOptions().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception{




        auth.userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder());

    }
}
