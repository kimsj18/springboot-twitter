package sj.springstudy4.springstudy4.comment;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;
import sj.springstudy4.springstudy4.post.Post;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Post_id")
    private Post post;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateContent(String content){
        this.content = content;
    }
}
