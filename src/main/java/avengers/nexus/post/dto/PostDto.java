package avengers.nexus.post.dto;

import avengers.nexus.post.domain.Post;
import avengers.nexus.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Setter
@Builder
public class PostDto {
    private String id;
    private String title;
    private String contents;
    private Long author;
    private boolean liked;
    private int totalCommentCount;   // 대댓글 수

    public static PostDto toDto(Post post){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int commentCount = post.getComments().size();
        int replyCount = post.getComments().stream()
                .mapToInt(comment -> comment.getReplies().size())
                .sum();
        int totalCommentCount = commentCount + replyCount;

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContent())
                .author(post.getAuthor())
                .liked(post.isLiked(currentUser))
                .totalCommentCount(totalCommentCount)
                .build();
    }
}
