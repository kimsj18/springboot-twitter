package sj.springstudy4.springstudy4.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sj.springstudy4.springstudy4.post.Post;
import sj.springstudy4.springstudy4.post.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse createComment(Long postId, CommentRequest request){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(request.content())
                .post(post)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Comment newComment = commentRepository.save(comment);

        //트랜잭션 오류 발생
//        if(Objects.equals(postId, 10L)){
//            throw new RuntimeException("애플리케이션 오류 발생");
//        }

        post.increaseCommentCount();
        postRepository.save(post);

        return CommentResponse.from(newComment);
    }

    public List<CommentResponse> getComments(Long postId){
       return commentRepository.findByPostIdOrderByIdDesc(postId)
               .stream()
               .map(commnet -> CommentResponse.from(commnet))
               .toList();

    }

    @Transactional
    public CommentResponse updateComment(Long postId, Long commentId, CommentRequest request){

        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        comment.updateContent(request.content());

//        commentRepository.save(comment);
        return CommentResponse.from(comment);
    }

    public void deleteComment(Long postId, Long commentId){

        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        Post post = comment.getPost();
        post.decreaseCommentCount();
        postRepository.save(post);
        commentRepository.delete(comment);
    }

    public List<CommentResponse> getAllComments(){
        // 전체조회 (모두)
        return commentRepository.findAll()
                .stream()
                .map(comment -> {
                    Post post = comment.getPost();
                    log.info("댓글 ID : {}, 게시글 ID: {}, 게시글 내용: {} ", comment.getId(), post.getId(), post.getContent() );
                    return CommentResponse.from(comment);
                })
                .toList();
    }

    public List<CommentResponse> getAllCommentsIn() {
        // In 절
        List<Comment> comments = commentRepository.findAll();

        Set<Long> postIds = comments.stream()
                .map(comment -> comment.getPost().getId())
                .collect(Collectors.toSet());

        Map<Long, Post> postMap = postRepository.findAllById(postIds)
                .stream()
                .collect(Collectors.toMap(Post::getId, Function.identity()));

        return comments
                .stream()
                .map(comment -> {
                    Post post = postMap.get(comment.getPost().getId());
                    log.info("댓글 ID : {}, 게시글 ID: {}, 게시글 내용: {} ", comment.getId(), post.getId(), post.getContent() );
                    return CommentResponse.from(comment);
                })
                .toList();
    }

//    public List<CommentResponse> getAllCommentsJPQL() {
    ////JPQL 사용
//        return commentRepository.findAll()
//                .stream()
//                .map(comment -> {
//                    Post post = comment.getPost();
//                    log.info("댓글 ID : {}, 게시글 ID: {}, 게시글 내용: {} ", comment.getId(), post.getId(), post.getContent() );
//                    return CommentResponse.from(comment);
//                })
//                .toList();
//    }

    public List<CommentResponse> getAllCommentsEntitiyGraph() {
        //EntityGraph 사용
        return commentRepository.findAll()
                .stream()
                .map(comment -> {
                    Post post = comment.getPost();
                    log.info("댓글 ID : {}, 게시글 ID: {}, 게시글 내용: {} ", comment.getId(), post.getId(), post.getContent() );
                    return CommentResponse.from(comment);
                })
                .toList();
    }
}
