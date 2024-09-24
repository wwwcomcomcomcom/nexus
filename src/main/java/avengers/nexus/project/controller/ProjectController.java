package avengers.nexus.project.controller;

import avengers.nexus.project.dto.CreateProjectDto;
import avengers.nexus.project.dto.SubmitApplicationDto;
import avengers.nexus.project.entity.Project;
import avengers.nexus.project.entity.Wanted;
import avengers.nexus.project.service.ProjectService;
import avengers.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        return user;
    }
    @GetMapping("/{id}")
    public Project getProject(@PathVariable String id) {
        return projectService.getProject(id);
    }
    @GetMapping("/")
    public List<Project> getAllProject(){
        return projectService.getAllProject();
    }
    @PostMapping("/")
    public ResponseEntity<?> createProject(@RequestBody CreateProjectDto project) {
        try {
            Project result = projectService.createProject(project);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Project creation failed!");
        }
    }
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }

    @PostMapping("/{projectId}/member")
    public ResponseEntity<?> addMember(@PathVariable String projectId, @RequestBody Long memberId) {
        User user = getAuthenticatedUser();
        Project project = projectService.getProject(projectId);
        if(!project.getOwner().equals(user.getId())) {
            return ResponseEntity.badRequest().body("Only owner can add member!");
        }
        if(project.getMembers().contains(memberId)) {
            return ResponseEntity.badRequest().body("Member already exists!");
        }
        if (user.getId().equals(memberId)) {
            return ResponseEntity.badRequest().body("Owner cannot be added as member!");
        }
        try{
            project.addMember(memberId);
            return ResponseEntity.ok("Member added successfully!");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Member addition failed!");
        }
    }

    @PostMapping("/{projectId}/wanted")
    public ResponseEntity<?> submitApplication(@PathVariable String projectId, @RequestBody SubmitApplicationDto applicationDto) {
        User user = getAuthenticatedUser();
        Project project = projectService.getProject(projectId);
        Wanted wanted = project.getWanted()
                .stream().filter(_wanted -> _wanted.equals(applicationDto.getWanted()))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wanted not found!"));
        if (wanted.getApplicants().contains(user.getId())) {
            return ResponseEntity.badRequest().body("Already applied!");
        }
        try {
            wanted.submitApplication(user.getId());
            return ResponseEntity.ok("Application submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Application submission failed!");
        }
    }
}
