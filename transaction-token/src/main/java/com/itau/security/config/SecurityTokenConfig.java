package com.itau.security.config;

import com.itau.security.property.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletResponse;

/**
 * @author vinicius.montouro
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    protected final JwtConfiguration jwtConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req,res,e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(jwtConfiguration.getLoginUrl())
                .permitAll()
                .antMatchers(jwtConfiguration.getLoginUrl(), "/**/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**").permitAll()
                .antMatchers("/fraude-cartao-credito/v1/fraude/cartao-credito").hasRole("ADMIN")
                .antMatchers("/fraude-relatorio/v1//transacao/relatorio").hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
