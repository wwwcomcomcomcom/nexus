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
    private int frontend;
    private int backend;
    private int android;
    private int ios;
    private int flutter;
    private int ai;
    private int design;
}
