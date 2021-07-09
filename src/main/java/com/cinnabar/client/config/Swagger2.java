package com.cinnabar.client.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2 // 启用Swagger2
@EnableSwaggerBootstrapUI
@ConfigurationProperties(prefix = "swagger")
@Getter
@Setter
public class Swagger2 {

    String title;
    String description;
    String version;
    String groupName;
    @Bean
    public Docket api() {
        //每个接口添加head参数start
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        tokenPar.name("Authorization").description("登录token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
        //添加head参数end

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.cinnabar.client.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(unifiedAuth())
                .apiInfo(apiInfo());
    }

    // swagger 设置全局同一添加header
    private static List<ApiKey> unifiedAuth() {
        ArrayList<ApiKey> arrayList = new ArrayList<>();
        arrayList.add(new ApiKey("app-type", "Authorization", "header"));
        return arrayList;
    }

    /* 创建该API的基本信息（这些基本信息会展现在文档页面中）

     * 访问地址：http://项目实际地址/swagger-ui.html

     * @return

     */

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()

                .title(title)

                .description(description)

                .termsOfServiceUrl("http://localhost:8761")

                .version(version)

                .build();

    }

}