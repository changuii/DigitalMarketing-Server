package dev.Store.Repository;

import dev.Store.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
    public Comment findByCommentWriter(String commentWriter);
}
