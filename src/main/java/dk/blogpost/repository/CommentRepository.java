package dk.blogpost.repository;

import dk.blogpost.moodel.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
