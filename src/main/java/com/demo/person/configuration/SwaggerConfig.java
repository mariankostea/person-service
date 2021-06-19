package com.demo.person.configuration;

import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter;
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.Filter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .directModelSubstitute(Void.class, String.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.demo.person.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Filter validationFilter() {
        return new OpenApiValidationFilter(
                true,
                false
        );
    }

    @Bean
    public WebMvcConfigurer addOpenApiValidationInterceptor(@Value("classpath:PersonServiceAPI.yaml") final Resource apiSpecification) throws IOException {
        final EncodedResource specResource = new EncodedResource(apiSpecification, StandardCharsets.UTF_8);
        final OpenApiValidationInterceptor openApiValidationInterceptor = new OpenApiValidationInterceptor(specResource);
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(final InterceptorRegistry registry) {
                registry.addInterceptor(openApiValidationInterceptor);
            }
        };
    }
}