package dev.Store.DAO;

import dev.Store.Entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface ProductDAO {
    public String create(ProductEntity product);
//    public Map<String, Object> readAllByPostNumber();
//    public Map<String, Object> readByNumber(Long number);
//    public String update(ProductEntity product);
//    public String delete(Long number);
}
