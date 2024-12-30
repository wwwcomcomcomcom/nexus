package avengers.nexus.project.service;

import avengers.nexus.project.dto.CreateProjectDto;
import avengers.nexus.project.entity.Project;
import avengers.nexus.project.repository.ProjectRepository;
import avengers.nexus.project.wanted.domain.Wanted;
import avengers.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<Project> getProjectsByPage(int index) {
        Pageable pageable = PageRequest.of(index, 10);
        Page<Project> page = projectRepository.findAll(pageable);
        List<Project> result = page.getContent();
        if(result.isEmpty()) {
            System.out.println("Project not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        return result;
    }
    public Project createProject(CreateProjectDto projectDto,User user) {
        Project project = Project.builder()
                        .title(projectDto.getTitle())
                        .subtitle(projectDto.getSubtitle())
                        .description(projectDto.getDescription())
                        .owner(user.getId())
                        .githubUrl(projectDto.getGithubUrl())
                        .state("모집중")

                .frontend(projectDto.getFrontend())
                .backend(projectDto.getBackend())
                .android(projectDto.getAndroid())
                .ios(projectDto.getIos())
                .flutter(projectDto.getFlutter())
                .ai(projectDto.getAi())
                .design(projectDto.getDesign())

                        .build();
        projectRepository.save(project);
        return project;
    }
    public void deleteProject(String id,User reqUser) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        if(project.get().getOwner().equals(reqUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only owner can delete project");
        }
        projectRepository.deleteById(id);
    }
}
