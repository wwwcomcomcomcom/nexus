package avengers.nexus.post.repository;
import avengers.nexus.post.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PostRepository extends MongoRepository<Post,String> {

    Post getPostById(String id);
}
