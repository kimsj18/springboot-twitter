package sj.springstudy4.springstudy4.post;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int commentCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void updateContent(String content){
        this.content = content;
    }

    public void increaseCommentCount(){
        this.commentCount++;
    }

    public void decreaseCommentCount(){
        this.commentCount--;
    }
}
