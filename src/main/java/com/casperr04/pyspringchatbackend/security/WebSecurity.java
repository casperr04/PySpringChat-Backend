package com.casperr04.pyspringchatbackend.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurity {
    private final AuthenticationProvider authenticationProvider;
    private final BearerAuthenticationFilter bearerAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/v1/auth/**", "/v1/user/info/**", "/v1/pm/**", "/swagger-ui/index.html", "/v3/api-docs")
                .permitAll()
                .requestMatchers("/v1/friend/remove/{username}",
                        "/v1/friend/request/accept/{username}",
                        "/v1/friend/request/send/{username}",
                        "/v1/channels/private-channel/create/{username}",
                        "/v1/channels/private-channel/message/{channelid}/{messageid}",
                        "/v1/channels/private-channel/check/{channelid}",
                        "/v1/friend/accept/{username}",
                        "/v1/friend/remove/{username}",
                        "/v1/friend/request/{username}",
                        "/chat-test",
                        "/demo/controller")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(bearerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}