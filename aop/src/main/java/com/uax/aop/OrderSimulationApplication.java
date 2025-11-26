package com.uax.aop;

import com.uax.aop.service.OrderSimulationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OrderSimulationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderSimulationApplication.class, args);
    }

    @Bean
    public CommandLineRunner runSimulation(OrderSimulationService simulationService) {
        return args -> simulationService.runSimulation();
    }
}
