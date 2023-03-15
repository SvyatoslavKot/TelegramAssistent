package ru.svyatoslavkotov.telegramassistent.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
@Data
@PropertySource("application.properties")
public class BotConfig {

        @Value("${bot.name}")
        String botName;

        @Value("${bot.token}")
        String token;

        @Bean
        RestTemplate restTemplate(){
              return new RestTemplate();
        }

}
