package com.techtest.serviciopersonacliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ServicioPersonaClienteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioPersonaClienteApplication.class, args);
    }

    @Bean
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

}
