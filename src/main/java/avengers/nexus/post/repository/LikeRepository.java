package avengers.nexus.post.repository;

import avengers.nexus.post.entity.Like;
import avengers.nexus.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    Like findByUserAndTarget(User user, Object target);

}
