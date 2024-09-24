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
    public void submitApplication(Long applicantId) {
        this.applicants.add(applicantId);
        this.neededMemberCount--;
    }
    public Wanted(String role, Integer neededMemberCount, List<String> stack) {
        this.role = role;
        this.neededMemberCount = neededMemberCount;
        this.stack = stack;
        this.applicants = List.of();
    }
    public boolean equals(Wanted wanted) {
        return this.role.equals(wanted.getRole()) && this.stack.equals(wanted.getStack()) && this.neededMemberCount.equals(wanted.getNeededMemberCount());
    }
}
