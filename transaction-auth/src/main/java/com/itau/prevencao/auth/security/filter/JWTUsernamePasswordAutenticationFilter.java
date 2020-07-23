package com.itau.prevencao.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.security.token.creator.TokenCreator;
import com.itau.security.model.ApplicationUser;
import com.itau.security.property.JwtConfiguration;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Collections.emptyList;

/**
 * @author vinicius.montouro
 */
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JWTUsernamePasswordAutenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final TokenCreator tokenCreator;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        log.info("Tentativa de autenticação");
        ApplicationUser applicationUser =
                new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
        if(applicationUser == null)
            throw  new UsernameNotFoundException("Não foi possível recuperar o nome ou senha do usuário");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(applicationUser.getName(), applicationUser.getPassword(), emptyList());

        usernamePasswordAuthenticationToken.setDetails(applicationUser);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    @SneakyThrows
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        log.info(String.format("Autenticação com sucesso para o usuário: '%s'",authResult.getName()));
        SignedJWT signedJWT = tokenCreator.newSignedJWT(authResult);
        String encryptToken = tokenCreator.encryptToken(signedJWT);
        log.info("Token gerado com sucesso, adicionando no header");
        response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKE, " + jwtConfiguration.getHeader().getName());
        response.addHeader(jwtConfiguration.getHeader().getName(), jwtConfiguration.getHeader().getPrefix() + encryptToken);
    }



}
