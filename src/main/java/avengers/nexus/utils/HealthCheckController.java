package avengers.nexus.utils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @RequestMapping("/health")
    public String healthCheck() {
        return "I'm alive!";
    }
}
