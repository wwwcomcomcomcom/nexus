package avengers.nexus.project.controller;

import avengers.nexus.auth.jwt.JWTUtil;
import avengers.nexus.project.dto.CreateProjectDto;
import avengers.nexus.project.entity.Project;
import avengers.nexus.project.service.ProjectService;
import avengers.nexus.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project", description = "프로젝트 관련 API")
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final JWTUtil jwtUtil;


    @GetMapping("/{id}")
    @Operation(summary = "프로젝트 조회", description = "해당 id의 프로젝트를 조회합니다.")
    @Parameter(name = "id", description = "프로젝트 ID", required = true)
    public Project getProject(@PathVariable String id) {
        return projectService.getProject(id);
    }


    @GetMapping("/")
    @Operation(summary = "모든 프로젝트 조회", description = "모든 프로젝트를 조회합니다.")
    public List<Project> getAllProject(){
        return projectService.getAllProject();
    }


    @GetMapping("/page/{page}")
    @Operation(summary = "페이지별 프로젝트 조회", description = "페이지별 프로젝트를 조회합니다.")
    @Parameter(name = "page", description = "페이지 번호", required = true)
    public List<Project> getProjectByPage(@PathVariable int page) {
        return projectService.getProjectsByPage(page);
    }


    @PostMapping("/")
    @Operation(summary = "프로젝트 생성", description = "프로젝트를 생성합니다.")
    @Parameter(name = "project", description = "CreateProjectDto", required = true)
    public ResponseEntity<?> createProject(@RequestBody CreateProjectDto project, @RequestHeader("Authorization") String authHeader) {
        User user = jwtUtil.getUserByAuthHeader(authHeader);
        try {
            Project result = projectService.createProject(project,user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Project creation failed!");
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "프로젝트 삭제", description = "프로젝트를 삭제합니다.")
    @Parameter(name = "id", description = "프로젝트 ID", required = true)
    public void deleteProject(@PathVariable String id,@RequestHeader("Authorization") String authHeader) {
        User user = jwtUtil.getUserByAuthHeader(authHeader);
        projectService.deleteProject(id, user);
    }

    @PostMapping("/{projectId}/member")
    public ResponseEntity<?> addMember(@PathVariable String projectId, @RequestBody Long memberId,@RequestHeader("Authorization") String authHeader) {
        User user = jwtUtil.getUserByAuthHeader(authHeader);
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
}
