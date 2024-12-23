package avengers.nexus.post.service;

import avengers.nexus.post.dto.CreatePostDto;
import avengers.nexus.post.repository.PostRepository;
import avengers.nexus.post.dto.PostDto;
import avengers.nexus.post.domain.Post;
import avengers.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public Post createPost(CreatePostDto post, Long authorId){
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setAuthor(authorId);
        return postRepository.save(newPost);
    }
    public void savePost(Post post) {
        postRepository.save(post);  // Post 객체를 데이터베이스에 저장
    }

    //전체 게시글 조회
    @Transactional(readOnly = true)
    public List<PostDto> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();

        posts.forEach(post -> postDtos.add(PostDto.toDto(post)));

        return postDtos;
    }

    //특정 게시글 조회
    @Transactional
    public PostDto  getPost(String id) {
        Post post = postRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Post Id를 찾을 수 없습니다.");
        });

        return PostDto.toDto(post);
    }

    public void likePost(String id, User user) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        post.addLike(user);
        postRepository.save(post);
    }
    public void dislikePost(String id,User user) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        post.removeLike(user);
        postRepository.save(post);
    }
    public void deletePost(String id,User user) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        if(post.getAuthor().equals(user.getId())) {
            postRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only author can delete post");
        }
    }
    //게시글 수정
//    @Transactional
//    public PostDto updatePost(String id, PostDto postDto, User user) {
//
//        Post post = postRepository.findById(id).orElseThrow(()->
//                new IllegalArgumentException("Post ID를 찾을 수 없습니다!"));
//
//        post.updatePost(postDto.getTitle(), postDto.getContents(), user);
//
//        postRepository.save(post);
//
//        return PostDto.toDto(post);
//    }

    //게시글 삭제
    @Transactional
    public void delete(String id){
        postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Board ID를 찾을 수 없습니다."));
        postRepository.deleteById(id);
    }
}