package com.hernandes.andrade.fiap.hackatonfiasub;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRole;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class HackatonFiaSubApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackatonFiaSubApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRoleRepository userRoleRepository) {
        return args -> {
            userRoleRepository.findByName("USER").orElseGet(() ->
                userRoleRepository.save(
                        UserRole.builder().name("USER").build()
                )
            );
        };
    }

}
