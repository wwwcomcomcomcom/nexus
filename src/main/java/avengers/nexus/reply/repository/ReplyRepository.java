package avengers.nexus.reply.repository;

import avengers.nexus.reply.entity.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends MongoRepository<Reply, String> {
    List<Reply> findAllByPostId(String postId);
}
