package dev.gateway.apigateway.repository;

import dev.gateway.apigateway.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // uid : email
    UserEntity getByUid(String uid);
}
