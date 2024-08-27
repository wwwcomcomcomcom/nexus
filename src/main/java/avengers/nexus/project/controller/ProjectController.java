package avengers.nexus.project.controller;

import avengers.nexus.project.dto.CreateProjectDto;
import avengers.nexus.project.entity.Project;
import avengers.nexus.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @GetMapping("/{id}")
    public Project getProject(@PathVariable String id) {
        return projectService.getProject(id);
    }
    @PostMapping("/")
    public ResponseEntity<?> createProject(@RequestBody CreateProjectDto project) {
        try {
            projectService.createProject(project);
            return ResponseEntity.ok("Project created!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Project creation failed!");
        }
    }
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }
}
