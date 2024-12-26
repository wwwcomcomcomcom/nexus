package avengers.nexus.post.service;

import avengers.nexus.post.dto.ReplyDto;
import avengers.nexus.post.domain.Reply;
import avengers.nexus.post.repository.PostRepository;

import avengers.nexus.post.repository.ReplyRepository;
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


    @Transactional
    public void writeReply(String postId, ReplyDto replyDto, User author) {
        postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        Reply reply = Reply.builder()
                .content(replyDto.getContent())
                .author(author)
                .postId(postId)
                .build();
        replyRepository.save(reply);
    }

    @Transactional(readOnly = true)
    public List<ReplyDto> getReplies(String postId) {
        List<Reply> replies = replyRepository.findAllByPostId(postId);
        List<ReplyDto> repliesDto = new ArrayList<>();
        replies.forEach(reply -> repliesDto.add(ReplyDto.toDto(reply)));
        return repliesDto;
    }

    @Transactional
    public void deleteReply(String postId, String replyId, User currentUser) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));


        if(!reply.getPostId().equals(postId)) {
            throw new IllegalArgumentException("해당 게시글에 속한 댓글이 아닙니다.");
        }

        if(!reply.getAuthor().equals(currentUser)) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }
        replyRepository.delete(reply);
    }

    //댓글 수정
//    @Transactional
//    public ReplyDto updateReply(String postId, String replyId, ReplyDto replyDto, User user) {
//        Reply reply = replyRepository.findById(replyId).orElseThrow(()->
//                new IllegalArgumentException("해당 댓글을  찾을 수 없습니다."));
//
//        if(!reply.getPost().getId().equals(postId)) {
//            throw new IllegalArgumentException("해당 게시글에 속한 댓글이 아닙니다.");
//        }
//
//        if(!reply.getUser().getId().equals(user.getId())) {
//            throw new SecurityException("수정 권한이 없습니다.");
//        }
//        reply.updateReply(replyDto.getContents(), user, reply.getPost());
//        replyRepository.save(reply);
//        return ReplyDto.toDto(reply);
//    }
}