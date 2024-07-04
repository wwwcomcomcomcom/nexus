package avengers.nexus.project.entity;

import avengers.nexus.utils.LongListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    //Wanted Role
    private Byte frontend;
    private Byte backend;
    private Byte android;
    private Byte ios;
    private Byte flutter;
    private Byte ai;
    private Byte design;

    private Long owner;
    @Convert(converter = LongListConverter.class)
    private List<Long> members = List.of();

    //null if not github registered
    private String githubUrl;

}
