package sj.springstudy4.springstudy4.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class postController {

    private final PostRepository postRepository;

//    @PostMapping("/api/posts")
//    public ResponseEntity<Post> createPost(@RequestBody Post post){
//        long newId = idGenerator.getAndIncrement();
//        Post newPost = new Post(newId, post.contents(), LocalDateTime.now());
//
//        posts.put(newId, newPost);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(newPost);
//    }

    @PostMapping("/api/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post) {

        Post newPost = Post.builder()
                .content(post.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        postRepository.save(newPost);
        return newPost;
    }

    @GetMapping("/api/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/api/posts/{id}")
    public Post getPosts(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow();
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePosts(@PathVariable Long id, @RequestBody Post postRequest) {
        return postRepository.findById(id)
                .map(post -> {
                    post.updateContent(postRequest.getContent());
                    return postRepository.save(post);
                })
                .orElseThrow();
    }

    @DeleteMapping("/api/posts/{id}")
    public void deletPost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }


    @GetMapping("/api/posts/search")
    public Slice<Post> searchPosts(
            @RequestParam(required = false) Long lastPostId,
            @RequestParam(defaultValue = "3") int size
    ) {
        int page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        if (lastPostId == null){
            return postRepository.findSlicesBy(pageable);
        }else {
            return postRepository.findSlicesByIdLessThan(lastPostId, pageable);
        }
    }

}
