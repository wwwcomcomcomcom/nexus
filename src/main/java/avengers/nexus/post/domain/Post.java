package avengers.nexus.post.domain;

import avengers.nexus.user.entity.User;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private User author;
    private Long likes;
    private List<User> likedBy; // 좋아요 누른 사용자 리스트
    private List<Comment> comments;

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
    }
    public void deleteComment(String commentId) {
        this.comments.removeIf(comment -> comment.getId().equals(commentId));
    }
    public void addLike() {
        this.likes++;
    }
    public void removeLike() {
        this.likes--;
    }
    public boolean isLiked(User user) {
        return this.likedBy.contains(user);
    }

}