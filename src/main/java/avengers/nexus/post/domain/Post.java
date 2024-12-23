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
    private Long author;
    private Long likes;
    private List<Long> likedBy; // 좋아요 누른 사용자 리스트
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
    public void addLike(User user) {
        Long id = user.getId();
        if(this.likedBy.contains(id)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 사용자입니다.");
        }
        this.likedBy.add(id);
        this.likes++;
    }
    public void removeLike(User user) {
        Long id = user.getId();
        if(!this.likedBy.contains(id)) {
            throw new IllegalArgumentException("좋아요를 누르지 않은 사용자입니다.");
        }
        this.likedBy.remove(id);
        this.likes--;
    }
    public boolean isLiked(User user) {
        return this.likedBy.contains(user.getId());
    }

}