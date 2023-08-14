package dev.Store.DAO;

import dev.Store.Entity.SalesPostEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface SalesPostDAO {
    public String create(SalesPostEntity salesPostEntity);
    public Map<String, Object> readAll();
    public Map<String, Object> readById(Long postNumber);
    public String update(SalesPostEntity salesPostEntity);
    public String delete(Long postNumber);
}
