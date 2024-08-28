package avengers.nexus.project.service;

import avengers.nexus.project.dto.CreateProjectDto;
import avengers.nexus.project.entity.Project;
import avengers.nexus.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public void deleteProject(String id) {
        if (!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        projectRepository.deleteById(id);
    }
}
