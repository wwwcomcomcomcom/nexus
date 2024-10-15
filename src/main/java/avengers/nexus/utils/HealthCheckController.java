package avengers.nexus.utils;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class HealthCheckController {
    @RequestMapping("/health")
    public String healthCheck() {
        return "I'm alive!";
    }
}
