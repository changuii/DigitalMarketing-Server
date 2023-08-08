package dev.gateway.apigateway.repository;


import dev.gateway.apigateway.Entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    UserEntity save(UserEntity userEntity);
    UserEntity getByUid(String uid);
    Boolean isEmailDuplicateCheck(String uid);
    void updateRoleByUid(String uid, String role);
}
