package avengers.nexus.project.controller;

import avengers.nexus.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/")
    ResponseEntity<?> getAllProjects(){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("not implemented");
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getProjectById(@PathVariable String projectId){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("not implemented");
    }

    @PostMapping("/")
    ResponseEntity<?> registerProject(){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("not implemented");
    }

}
