package sj.springstudy4.springstudy4.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest (

        @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
        @Size(min = 1, max = 50, message = "댓글은 1자 이상 50자 이하여야 합니다.")
        String content
){

}

