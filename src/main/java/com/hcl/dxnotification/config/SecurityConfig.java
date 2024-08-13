package com.hcl.dxnotification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.hcl.dxnotification.security.LtpaTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/notifications/**").permitAll() // Permit all access to Swagger UI and notifications API
                .anyRequest().authenticated()
            .and()
            .csrf().disable(); // Disable CSRF protection
        //use LTPA token filter to process inbound security based on ltpa token
        //http.addFilterBefore(new LtpaTokenFilter(), BasicAuthenticationFilter.class);
    }
}
 
