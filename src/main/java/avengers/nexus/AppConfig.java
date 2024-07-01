package avengers.nexus;

import gauth.GAuth;
import gauth.impl.GAuthImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate(){return new RestTemplate();}
    @Bean
    public GAuth gAuth(){return new GAuthImpl();}
}
