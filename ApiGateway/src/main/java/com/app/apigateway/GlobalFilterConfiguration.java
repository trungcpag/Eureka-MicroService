package com.app.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {

    final Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

    @Order(1)
    @Bean
    public GlobalFilter secondPreFilter(){
        return (exchange, chain) -> {
            logger.info("my second global prefilter");
          return chain.filter(exchange).then(Mono.fromRunnable(() ->{
              logger.info("my second global postFilter");
          }));
        };
    }
    @Order(2)
    @Bean
    public GlobalFilter thirdPreFilter(){
        return (exchange, chain) -> {
            logger.info("my third global prefilter");
            return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                logger.info("my third global postFilter");
            }));
        };
    }

    @Order(3)
    @Bean
    public GlobalFilter fourthPreFilter(){
        return (exchange, chain) -> {
            logger.info("my fourth global prefilter");
            return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                logger.info("my fourth global postFilter");
            }));
        };
    }
}
