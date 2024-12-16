package avengers.nexus.post.repository;

import avengers.nexus.post.domain.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends MongoRepository<Reply, String> {
    List<Reply> findAllByPostId(String postId);
}