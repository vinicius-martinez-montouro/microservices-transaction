package com.itau.security.token.creator;

import com.itau.security.model.ApplicationUser;
import com.itau.security.property.JwtConfiguration;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

/**
 * @author vinicius.montouro
 */
@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenCreator {
    private final JwtConfiguration jwtConfiguration;

    @SneakyThrows
    public SignedJWT newSignedJWT(Authentication auth){
        log.info("Criando nova assinatura JWT");
        ApplicationUser applicationUser = (ApplicationUser) auth.getPrincipal();

        JWTClaimsSet jwtClaimsSet = newJWTClaimsSet(auth, applicationUser);

        KeyPair keyPair = generateKeyPair();

        JWK jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).keyID(UUID.randomUUID().toString()).build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .jwk(jwk)
                .type(JOSEObjectType.JWT)
                .build(), jwtClaimsSet);

        log.info("Assinando token com o private RSA key");

        RSASSASigner rsassaSigner = new RSASSASigner(keyPair.getPrivate());

        signedJWT.sign(rsassaSigner);

        return signedJWT;
    }

    private JWTClaimsSet newJWTClaimsSet(Authentication auth, ApplicationUser applicationUser){
        log.info("Criando Calims Set ");
        return new JWTClaimsSet.Builder()
                .subject(applicationUser.getName())
                .claim("authorities", auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(toList()))
                .claim("userId", applicationUser.getId())
                .issuer("https://itau.transacao")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (jwtConfiguration.getExpiration() * 1000)))
                .build();
    }

    @SneakyThrows
    private KeyPair generateKeyPair(){
        log.info("Gerando chaves RSA 2048");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.genKeyPair();
    }

    public String encryptToken(SignedJWT signedJWT) throws JOSEException {
        log.info("Criptografando . . .");

        DirectEncrypter directEncrypter = new DirectEncrypter(jwtConfiguration.getPrivateKey().getBytes());
        JWEObject jwt = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
                .contentType("JWT")
                .build(), new Payload(signedJWT));
        jwt.encrypt(directEncrypter);
        return jwt.serialize();
    }

}
