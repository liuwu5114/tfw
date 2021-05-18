package com.tongtech.tfw.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@SpringBootConfiguration
public class KnifeSwaggerConfiguration {
  @Value("${swagger.enable}")
  boolean enable;

  @Value("${swagger.title}")
  String title;

  @Value("${swagger.description}")
  String description;

  @Value("${swagger.contact.name}")
  String contactName;

  @Value("${swagger.contact.url}")
  String contactUrl;

  @Value("${swagger.contact.mail}")
  String contactMail;

  @Value("${swagger.version}")
  String version;

  @Bean
  public Docket allApi() {
    if (!enable) {
      return new Docket(DocumentationType.OAS_30)
          .enable(false)
          .select()
          // 指定扫描的包
          .paths(PathSelectors.none())
          .build();
    } else {
      ApiInfo apiInfo =
          (new ApiInfoBuilder())
              .title(title)
              .description(description)
              .contact(new Contact(contactName, contactUrl, contactMail))
              .version(version)
              .build();

      List<RequestParameter> pars = new ArrayList<>();
      RequestParameterBuilder ticketPar = new RequestParameterBuilder();
      ticketPar
          .name("Authorization") // FrameworkConstants.AUTHORIZATION_HEADER
          .description("user token")
          .in(ParameterType.HEADER)
          .required(false)
          .build(); // header中的ticket参数非必填，传空也可以
      RequestParameterBuilder timestampPar = new RequestParameterBuilder();
      timestampPar
          .name("timestamp") // FrameworkConstants.AUTHORIZATION_HEADER
          .description("timestamp")
          .in(ParameterType.HEADER)
          .required(false)
          .build(); // header中的ticket参数非必填，传空也可以
      RequestParameterBuilder signPar = new RequestParameterBuilder();
      signPar
          .name("sign") // FrameworkConstants.AUTHORIZATION_HEADER
          .description("data sign")
          .in(ParameterType.HEADER)
          .required(false)
          .build(); // header中的ticket参数非必填，传空也可以

      pars.add(ticketPar.build());
      pars.add(timestampPar.build());
      pars.add(signPar.build());

      return new Docket(DocumentationType.OAS_30)
          .useDefaultResponseMessages(false)
          .apiInfo(apiInfo)
          .select()
          // 指定扫描的包
          .apis(RequestHandlerSelectors.basePackage("com.tongtech"))
          .paths(PathSelectors.any())
          .build()
          .globalRequestParameters(pars);
    }
  }
}
