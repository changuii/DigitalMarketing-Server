package dev.Store.DAO;

import dev.Store.Entity.Comment;
import dev.Store.Entity.SalesPostEntity;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public interface SalesPostDAO {
    public Map<String, Object> create(SalesPostEntity salesPostEntity);
    public String createPostComment(Long salesPostNumber, Comment comment);
    public Map<String, Object> readByWriterAndTitle(Long salesPostNumber);
    public Map<String, Object> readAllByCategory(String category);
    public Map<String, Object> readAll();
    public String update(SalesPostEntity salesPostEntity);
    public Map<String, Object> postLikeUpdate(Long salesPostNumber, Boolean like);
    public Map<String, Object> postHitCountUpdate(Long salesPostNumber);
    public String delete(Long salesPostNumber);
    public String deleteComment(Long salesPostNumber, String commentWriter);
}
