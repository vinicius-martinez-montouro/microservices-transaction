package com.itau.security.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author vinicius.montouro
 */
@Configuration
@ConfigurationProperties(prefix = "jwt.config")
@Getter
@Setter
@ToString
public class JwtConfiguration {
    private final String loginUrl = "/login/**";

    @NestedConfigurationProperty
    private Header header = new Header();
    private int expiration = 3600;
    private String privateKey = "c2v0o2kvrtS7ThBmUMbKegW9TaDmHm8H";
    private String type = "encrypted";

    @Data
    public static class Header{
        private String name = "Authorization";
        private String prefix = "Bearer ";
    }

}
