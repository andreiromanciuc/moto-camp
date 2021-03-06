package org.fasttrackit.motocamp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserPrincipalDetailsService userPrincipalDetailsService;

    public ApplicationSecurityConfig(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/",
                        "/css/**",
                        "/fonts/**",
                        "/images/**",
                        "/js/**",
                        "/js/signup",
                        "/js/signup_motor").permitAll()
                .antMatchers("/signup/**").permitAll()
                .antMatchers("/createMotorcycle/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers(HttpMethod.GET, "/newsfeed/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/newsfeed/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/newsfeed/**").authenticated()
                .antMatchers(HttpMethod.GET, "/timeline/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/timeline/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/timeline/**").authenticated()
                .anyRequest().authenticated()
                .and()
//                .httpBasic();
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/newsfeed").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login");
    }


    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

