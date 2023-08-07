package com.example.sales_post.Repository;

import com.example.sales_post.Entity.SalesPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPostRepository extends JpaRepository<SalesPostEntity, Long> {

}
