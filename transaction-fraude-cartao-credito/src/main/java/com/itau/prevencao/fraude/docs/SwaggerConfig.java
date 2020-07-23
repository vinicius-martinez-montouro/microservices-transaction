package com.itau.prevencao.fraude.docs;

import com.itau.core.docs.BaseSwaggerConfig;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author vinicius.montouro
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig extends BaseSwaggerConfig {

    public SwaggerConfig() {
        super("com.itau.prevencao.fraude.endpoint.controller");
    }
}
