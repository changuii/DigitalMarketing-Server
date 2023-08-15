package dev.Store.DAO;

import dev.Store.Entity.SalesPostEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SalesPostDAO {
    public String create(SalesPostEntity salesPostEntity);
    public Map<String, Object> readByWriterAndTitle(String postWriter, String postTitle);
    public Map<String, Object> readAllByCategory(String category);
    public Map<String, Object> readAll();
    public String update(SalesPostEntity salesPostEntity);
    public Map<String, Object> postLikeUpdate(String postTitle, Long postLike, String action);
    public Map<String, Object> postHitCountUpdate(String postTitle, Long postHitCount);
    public String delete(String postWriter, String postTitle);
}
