package com.ahicode.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${authentication.service.url}")
    private String authServiceUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_service", route -> route.path("/api/v1/auth/**")
                        .uri(authServiceUrl)
                )
                .build();

        //    .route("auth_service", r -> r.path("/api/v1/auth/**")
        //           .uri("lb://YOUR-SERVICE-NAME")) for Eureka
    }
}
