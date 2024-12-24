package avengers.nexus.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentSummaryDto {
    private int commentCount;
    private int replyCount;
}
