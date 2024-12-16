package avengers.nexus.post.domain;

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

    public Bookmark() {
        this.userId = null;
        this.postIds = new ArrayList<>();
    }

    public void addPost(Post postId) {
        if (!this.postIds.contains(postId)) {
            this.postIds.add(postId);
        }
    }

    public void removePost(Post postId) {
        this.postIds.remove(postId);
    }

    public boolean isBookmarked(Post postId){
        return this.postIds.contains(postId);
    }


}
