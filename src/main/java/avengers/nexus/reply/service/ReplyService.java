package avengers.nexus.reply.service;

import avengers.nexus.post.entity.Post;
import avengers.nexus.post.repository.PostRepository;
import avengers.nexus.reply.dto.ReplyDto;
import avengers.nexus.reply.entity.Reply;
import avengers.nexus.reply.repository.ReplyRepository;
import avengers.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    //댓글 작성
    @Transactional
    public void writeReply(String postId, ReplyDto replyDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(()->{return new IllegalArgumentException("게시판을 찾을 수 없습니다.");
        });

        Reply reply = Reply.builder()
                .contents(replyDto.getContents())
                .user(user)
                .post(post)
                .build();

        replyRepository.save(reply);
    }

    //특정 게시글에 해당하는 전체 댓글 불러오기
    @Transactional(readOnly = true)
    public List<ReplyDto> getReplies(String postId) {
        List<Reply> replies = replyRepository.findAllByPostId(postId);
        List<ReplyDto> repliesDto = new ArrayList<>();

        replies.forEach(reply -> repliesDto.add(ReplyDto.toDto(reply)));
        return repliesDto;
    }

    //댓글 삭제
    @Transactional
    public void deleteReply(String postId, String replyId, User currentUser) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(()->
            new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));


        if(!reply.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        replyRepository.delete(reply);
    }


    //댓글 수정
    @Transactional
    public ReplyDto updateReply(String postId, String replyId, ReplyDto replyDto, User user) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(()->
            new IllegalArgumentException("해당 댓글을  찾을 수 없습니다."));


        if(!reply.getUser().getId().equals(user.getId())) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        reply.updateReply(replyDto.getContents(), user, reply.getPost());
        replyRepository.save(reply);
        return ReplyDto.toDto(reply);
    }

}
