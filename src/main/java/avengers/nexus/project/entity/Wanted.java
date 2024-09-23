package avengers.nexus.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Wanted {
    private String role;
    private Integer neededMemberCount;
    private List<String> stack;
}
