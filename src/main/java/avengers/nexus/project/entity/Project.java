package avengers.nexus.project.entity;

import avengers.nexus.project.wanted.domain.Wanted;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Project {
    @Id
    private String id;
    private String title;
    private String subtitle;
    private String description;

    private Long owner;
    private List<Long> members = List.of();

    //null if not github registered
    private String githubUrl;

    private String state;

    private int frontend;
    private int backend;
    private int android;
    private int ios;
    private int flutter;
    private int ai;
    private int design;

//    private List<Wanted> wanted = List.of();

    public void addMember(Long memberId) {
        members.add(memberId);
    }
    public void removeMember(Long memberId) {
        members.remove(memberId);
    }
//    public void addWanted(Wanted wanted) {
//        this.wanted.add(wanted);
//    }
//    public void removeWanted(Wanted wanted) {
//        this.wanted.remove(wanted);
//    }
}
