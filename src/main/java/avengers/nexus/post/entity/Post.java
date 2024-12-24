package avengers.nexus.post.entity;

import avengers.nexus.post.domain.Comment;
import avengers.nexus.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private User author;
    private Long likes;
    private List<User> likedBy; // 좋아요 누른 사용자 리스트
    private List<Comment> comments;

    // 댓글 수 반환
    public int getCommentCount() {
        return comments.size();
    }

    // 대댓글 수 반환
    public int getReplyCount() {
        return comments.stream()
                .mapToInt(comment -> comment.getReplies().size())
                .sum();
    }
}
