package sj.springstudy4.springstudy4.comment;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
//@RequestMapping("/api/posts/{postId}/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public CommentResponse creatComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request
    ){
        // 댓글 생성
        return commentService.createComment(postId, request);

    }

    @GetMapping("/api/posts/{postId}/comments")
    public List<CommentResponse> getComments(@PathVariable Long postId){
        // 댓글 조회
        return commentService.getComments(postId);
    }

    @PutMapping("/api/posts/{postId}/comments/{commentId}")
    public CommentResponse updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request
    ){
        // 댓글 수정
        return commentService.updateComment(postId, commentId,  request);
    }

    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public void deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ){
        // 댓글 삭제
        commentService.deleteComment(postId, commentId);
    }

    @GetMapping("/api/comments")
    public List<CommentResponse> getAllComments(){
        return commentService.getAllComments();
    }

    @GetMapping("/api/commentsIn")
    public List<CommentResponse> getAllCommentsIn(){
        return commentService.getAllCommentsIn();
    }

//    @GetMapping("/api/commentsJPQL")
//    public List<CommentResponse> getAllCommentsJPQL(){
//        return commentService.getAllCommentsJPQL();
//    }

    @GetMapping("/api/commentsEntityGraph")
    public List<CommentResponse> getAllCommentsEntityGraph(){
        return commentService.getAllCommentsEntitiyGraph();
    }

}
