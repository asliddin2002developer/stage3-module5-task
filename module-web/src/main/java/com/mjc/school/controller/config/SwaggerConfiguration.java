package com.mjc.school.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiEndPointInfo())
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.mjc.school.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiEndPointInfo() {
        return new ApiInfoBuilder().title("News management application Rest API")
            .description("News management application Rest API for CRUD operations with resources")
            .contact(new Contact("MJC School", "https://mjc.school/", "asldev@epam.com"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("0.0.1-SNAPSHOT")
            .build();
    }
}
