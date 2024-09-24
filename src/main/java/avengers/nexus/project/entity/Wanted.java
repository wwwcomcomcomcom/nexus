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
    private List<Long> applicants;
    public Wanted(String role, Integer neededMemberCount, List<String> stack) {
        this.role = role;
        this.neededMemberCount = neededMemberCount;
        this.stack = stack;
        this.applicants = List.of();
    }
}
