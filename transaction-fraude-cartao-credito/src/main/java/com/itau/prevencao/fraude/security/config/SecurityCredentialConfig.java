package com.itau.prevencao.fraude.security.config;

import com.itau.security.config.SecurityTokenConfig;
import com.itau.security.filter.JwtTokenAuthorizationFilter;
import com.itau.security.property.JwtConfiguration;
import com.itau.security.token.converter.TokenConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author vinicius.montouro
 */
@EnableWebSecurity
public class SecurityCredentialConfig extends SecurityTokenConfig {
    private final TokenConverter tokenConverter;

    public SecurityCredentialConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAfter(new JwtTokenAuthorizationFilter(jwtConfiguration,tokenConverter),
                    UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }

}
