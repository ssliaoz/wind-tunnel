package com.windtunnel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API文档配置类
 * 
 * 配置OpenAPI文档相关信息，包括API信息、安全配置等
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Configuration
public class SwaggerConfig {

    /**
     * 创建OpenAPI配置
     * 
     * @return OpenAPI配置实例
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                    .addSecuritySchemes("Bearer Authentication", 
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .description("输入 JWT token 进行认证")
                    )
                );
    }

    /**
     * 定义API信息
     * 
     * @return API信息对象
     */
    private Info apiInfo() {
        return new Info()
                .title("智慧风洞管理系统 API")
                .description("智慧风洞管理系统后端API文档")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Wind Tunnel Development Team")
                    .email("dev@windtunnel.com")
                    .url("https://www.windtunnel.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0"));
    }

}