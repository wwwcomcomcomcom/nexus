package avengers.nexus.post.Service;

import avengers.nexus.post.Repository.PostRepository;
import avengers.nexus.post.dto.CreatePostDto;
import avengers.nexus.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post getPostById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }
    public Post createPost(CreatePostDto post){
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setAuthor(post.getAuthor());
        return postRepository.save(newPost);
    }
    public void savePost(Post post) {
        postRepository.save(post);
    }
    public void likePost(String id) {

    }
    public void dislikePost(String id) {

    }
    public void deletePost(String id){

    }
}
