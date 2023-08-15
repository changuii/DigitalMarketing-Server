package dev.Store.DAO;

import dev.Store.Entity.SalesPostEntity;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public interface SalesPostDAO {
    public String create(SalesPostEntity salesPostEntity);
    public Map<String, Object> readAll();
    public Map<String, Object> readByWriterAndTitle(String postWriter, String postTitle);
    public String update(SalesPostEntity salesPostEntity);
    public String delete(String postWriter, String postTitle);
}
