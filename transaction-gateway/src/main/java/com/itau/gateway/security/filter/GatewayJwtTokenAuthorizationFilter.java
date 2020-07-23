package com.itau.gateway.security.filter;

import com.itau.security.filter.JwtTokenAuthorizationFilter;
import com.itau.security.property.JwtConfiguration;
import com.itau.security.token.converter.TokenConverter;
import com.netflix.zuul.context.RequestContext;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.itau.security.token.util.SecurityContextUtil.setSecurityContext;

/**
 * @author vinicius.montouro
 */
public class GatewayJwtTokenAuthorizationFilter extends JwtTokenAuthorizationFilter {
    public GatewayJwtTokenAuthorizationFilter(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration, tokenConverter);
    }

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) {
        String header = request.getHeader(jwtConfiguration.getHeader().getName());

        if (header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();

        String decryptToken = tokenConverter.decryptToken(token);

        tokenConverter.validateTokenSignature(decryptToken);

        setSecurityContext(SignedJWT.parse(decryptToken));

        if(jwtConfiguration.getType().equalsIgnoreCase("signed")){
            RequestContext.getCurrentContext()
                    .addZuulRequestHeader("Authorization", jwtConfiguration.getHeader().getPrefix() + decryptToken);
        }

        chain.doFilter(request, response);
    }
}
