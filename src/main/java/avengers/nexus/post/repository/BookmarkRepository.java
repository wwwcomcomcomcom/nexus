package avengers.nexus.post.repository;

import avengers.nexus.post.domain.Bookmark;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends MongoRepository<Bookmark, String> {
    List<Bookmark> findByUserId(String userId);
    Optional<Bookmark> findByUserIdAndPostIdsContaining(String userId, String postId);
}
