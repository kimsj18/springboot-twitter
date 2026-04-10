package sj.springstudy4.springstudy4.post;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Slice<Post> findSlicesBy(Pageable pageable);

    Slice<Post> findSlicesByIdLessThan(Long lastId, Pageable pageable);
}
