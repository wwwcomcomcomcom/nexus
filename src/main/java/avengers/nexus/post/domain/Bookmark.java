package avengers.nexus.post.domain;

import avengers.nexus.post.dto.PostDto;
import avengers.nexus.post.entity.Post;
import avengers.nexus.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Bookmark {
    private String id;

    private User userId;
    private List<Post> postIds;

    public Bookmark(User userId) {
        this.userId = userId;
        this.postIds = new ArrayList<>();
    }

    public void addPostId(avengers.nexus.post.entity.Post postId) {
        if (!this.postIds.contains(postId)) {
            this.postIds.add(postId);
        }
    }

    public void removePostId(Post postId) {
        this.postIds.remove(postId);
    }

    public boolean isBookmarked(Post postId){
        return this.postIds.contains(postId);
    }

    public void clearBookmarks() {
        this.postIds.clear();
    }

}
