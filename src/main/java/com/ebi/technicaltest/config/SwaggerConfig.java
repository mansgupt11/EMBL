package com.ebi.technicaltest.config;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

  @Value("${spring.application.name}")
  private String applicationName;

  @Bean
  public Docket postsApi() {
    final String identityHeader = "Bearer";
    final String header = "header";
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.ebi"))
        .paths(PathSelectors.any())
        .build()
        .globalResponseMessage(RequestMethod.GET, getGlobalResponses())
        .globalOperationParameters(Collections.singletonList(
            new ParameterBuilder().name(identityHeader)
                .description("JWT Authentication Token, as populated from OpenIG.")
                .modelRef(new ModelRef(String.class.getSimpleName()))
                .parameterType(header)
                .required(false)
                .build()
        ));
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(String.format("%s API", applicationName))
        .description(String.format("%s API reference for developers", applicationName))
        .version("1")
        .build();
  }

  private List<ResponseMessage> getGlobalResponses() {
    return Arrays.asList(
        new ResponseMessageBuilder().code(OK.value()).message("OK").build(),
        new ResponseMessageBuilder().code(FORBIDDEN.value()).message("Invalid JWT token").build()
    );
  }

}
