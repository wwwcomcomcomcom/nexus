package avengers.nexus.post.service;

import avengers.nexus.post.repository.PostRepository;
import avengers.nexus.post.dto.PostDto;
import avengers.nexus.post.entity.Post;
import avengers.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

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
    public PostDto getPost(String id) {
        Post post = postRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Post Id를 찾을 수 없습니다.");
        });

        return PostDto.toDto(post);
    }

    //게시글 작성
    @Transactional
    public PostDto writePost(PostDto postDto, User user) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .contents(postDto.getContents())
                .author(user)
                .build();

        postRepository.save(post);
        return PostDto.toDto(post);
    }

    //게시글 수정
    @Transactional
    public PostDto updatePost(String id, PostDto postDto, User user) {

        Post post = postRepository.findById(id).orElseThrow(()->
           new IllegalArgumentException("Post ID를 찾을 수 없습니다!"));

        post.updatePost(postDto.getTitle(), postDto.getContents(), user);

        postRepository.save(post);

        return PostDto.toDto(post);
    }

    //게시글 삭제
    @Transactional
    public void delete(String id){
        Post post = postRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Board ID를 찾을 수 없습니다.");
        });

        postRepository.deleteById(id);
    }
}
