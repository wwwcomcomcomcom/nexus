package avengers.nexus.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectDto {
    private String title;
    private String subtitle;
    private String description;
    private String githubUrl;
    private Long owner;
}
