package avengers.nexus.project.service;

import avengers.nexus.project.dto.CreateProjectDto;
import avengers.nexus.project.entity.Project;
import avengers.nexus.project.entity.Wanted;
import avengers.nexus.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project getProject(String id) {
        return projectRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }
    public Project createProject(CreateProjectDto projectDto) {
        Project project = Project.builder()
                        .title(projectDto.getTitle())
                        .subtitle(projectDto.getSubtitle())
                        .description(projectDto.getDescription())
                        .owner(projectDto.getOwner())
                        .githubUrl(projectDto.getGithubUrl())
                        .build();
        projectRepository.save(project);
        return project;
    }
    public void deleteProject(String id,Long ownerId) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        if(project.get().getOwner().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only owner can delete project");
        }
        projectRepository.deleteById(id);
    }

    public Wanted registerWanted(String projectId,Long ownerId, Wanted wanted) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        if(!Objects.equals(project.getOwner(), ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only owner can register wanted");
        }
        project.addWanted(wanted);
        projectRepository.save(project);
        return wanted;
    }
    public void deleteWanted(String projectId,Long ownerId, Wanted wanted) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        if(project.getWanted().stream().noneMatch(w -> w.equals(wanted))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wanted not found");
        }
        if(!Objects.equals(project.getOwner(), ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only owner can delete wanted");
        }
        project.removeWanted(wanted);
        projectRepository.save(project);
    }
    public void submitApplication(String projectId, Long userId, Wanted reqWanted) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        Wanted wanted = project.getWanted().stream()
                .filter(w -> w.equals(reqWanted))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wanted not found"));
        wanted.addApplicant(userId);
        projectRepository.save(project);
    }
    public void cancelApplication(String projectId, Long userId, Wanted reqWanted) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        Wanted wanted = project.getWanted().stream()
                .filter(w -> w.equals(reqWanted))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wanted not found"));
        wanted.removeApplicant(userId);
        projectRepository.save(project);
    }
    public void acceptApplication(String projectId,Long ownerId, Long userId, Wanted reqWanted) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        if(!project.getOwner().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only owner can accept application");
        }
        Wanted wanted = project.getWanted().stream()
                .filter(w -> w.equals(reqWanted))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wanted not found"));
        wanted.removeApplicant(userId);
        wanted.decreaseNeedMember();
        if(wanted.getNeededMemberCount() == 0) {
            project.removeWanted(wanted);
        }
        project.addMember(userId);
        projectRepository.save(project);
    }
    public void rejectApplication(String projectId,Long ownerId, Long userId, Wanted reqWanted) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        if(!project.getOwner().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only owner can reject application");
        }
        Wanted wanted = project.getWanted().stream()
                .filter(w -> w.equals(reqWanted))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wanted not found"));
        wanted.removeApplicant(userId);
        projectRepository.save(project);
    }
}
