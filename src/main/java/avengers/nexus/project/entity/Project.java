package avengers.nexus.project.entity;

import avengers.nexus.utils.LongListConverter;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    private Long id;
    private String name;
    private String description;

    private Long owner;
    private List<Long> members = List.of();

    //null if not github registered
    private String githubUrl;

    private String state;
    private String stateDescription;

    private List<Wanted> wanted = List.of();

}
