package avengers.nexus.project.wanted.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Wanted {
    private String role;
    private Integer neededMemberCount;
    private List<String> stack;
    private List<Long> applicants;
    public void addApplicant(Long userId) {
        applicants.add(userId);
    }
    public void removeApplicant(Long userId) {
        applicants.remove(userId);
    }
    public void decreaseNeedMember(){
        neededMemberCount--;
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
