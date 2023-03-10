package com.devper.tracker.config;

import com.devper.common.config.BaseWebSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class TrackerWebSecurityConfig extends BaseWebSecurityConfig {

    @Bean
    @Override
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/transactions/**").authenticated();
        return super.filterChain(http);

    }
}
