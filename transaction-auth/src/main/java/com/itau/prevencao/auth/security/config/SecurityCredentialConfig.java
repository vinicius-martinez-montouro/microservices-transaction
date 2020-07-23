package com.itau.prevencao.auth.security.config;

import com.itau.prevencao.auth.security.filter.JWTUsernamePasswordAutenticationFilter;
import com.itau.security.config.SecurityTokenConfig;
import com.itau.security.filter.JwtTokenAuthorizationFilter;
import com.itau.security.token.converter.TokenConverter;
import com.itau.security.token.creator.TokenCreator;
import com.itau.security.property.JwtConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author vinicius.montouro
 */
@EnableWebSecurity
public class SecurityCredentialConfig extends SecurityTokenConfig {
    private final UserDetailsService userDetailsService;
    private final TokenCreator tokenCreator;
    private final TokenConverter tokenConverter;

    public SecurityCredentialConfig(UserDetailsService userDetailsService,TokenCreator tokenCreator,
                                    JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.userDetailsService = userDetailsService;
        this.tokenCreator = tokenCreator;
        this.tokenConverter = tokenConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilter(new JWTUsernamePasswordAutenticationFilter(authenticationManager(),jwtConfiguration, tokenCreator))
            .addFilterAfter(new JwtTokenAuthorizationFilter(jwtConfiguration,tokenConverter),
                    UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
