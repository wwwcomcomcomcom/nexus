package avengers.nexus;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.SocketIOServer;
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

    @Bean
    public SocketIOServer socketIOServer(AuthorizationListener authorizationListener){
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(8081);
        config.setBossThreads(1);
        config.setWorkerThreads(2);
        config.setAuthorizationListener(authorizationListener);
        return new SocketIOServer(config);
    }
}
