package avengers.nexus.project.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {
    @Id
    private Long id;
    private String title;
    private String subtitle;
    private String description;

    private Long owner;
    private List<Long> members = List.of();

    //null if not github registered
    private String githubUrl;

    private String state;
    private String stateDescription;

    private List<Wanted> wanted = List.of();

}
