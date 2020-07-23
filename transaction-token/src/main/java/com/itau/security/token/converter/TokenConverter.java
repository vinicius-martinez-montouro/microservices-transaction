package com.itau.security.token.converter;

import com.itau.security.property.JwtConfiguration;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


/**
 * @author vinicius.montouro
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenConverter {
    private final JwtConfiguration jwtConfiguration;

    @SneakyThrows
    public String decryptToken(String encryptedToken) {
        log.info("Descriptografando token");

        JWEObject jweObject = JWEObject.parse(encryptedToken);

        DirectDecrypter directDecrypter = new DirectDecrypter(jwtConfiguration.getPrivateKey().getBytes());

        jweObject.decrypt(directDecrypter);

        log.info("Token decryptadp");

        return jweObject.getPayload().toSignedJWT().serialize();
    }

    @SneakyThrows
    public void validateTokenSignature(String signedToken) {
        log.info("Validando assinatura token");

        SignedJWT signedJWT = SignedJWT.parse(signedToken);
        RSAKey publicKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());
        if (!signedJWT.verify(new RSASSAVerifier(publicKey)))
            throw new AccessDeniedException("Assinatura do token inválida!");

        log.info("Assinatura token válido");
    }
}
