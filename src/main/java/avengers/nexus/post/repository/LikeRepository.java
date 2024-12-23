package avengers.nexus.post.repository;

import avengers.nexus.post.domain.Like;
import avengers.nexus.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    Like findByUserAndTarget(User user, Object target);
    List<Like> findAllByTarget(Object target);

}
