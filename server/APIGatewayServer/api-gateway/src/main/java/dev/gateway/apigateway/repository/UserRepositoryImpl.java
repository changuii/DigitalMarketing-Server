package dev.gateway.apigateway.repository;


import dev.gateway.apigateway.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(
            @Autowired JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        String sql = "INSERT INTO User(uid, password, name, role, gender, age, birthday, address, likes) VALUES(?,?,?,?,?,?,?,?,?)";
        int update = jdbcTemplate.update(sql, userEntity.getUid(), userEntity.getPassword(), userEntity.getName(),
                userEntity.getRoles().get(0), userEntity.getGender(), userEntity.getAge(), userEntity.getBirthday(), userEntity.getAddress(), userEntity.getLikes());
        System.out.println(update + "개 데이터 생성");

        return userEntity;
    }

    @Override
    public UserEntity getByUid(String uid) { // optional = null일 수도 있는 객체를 감싸는 Wrapper클래스
        String sql = "SELECT * FROM User WHERE uid = ?";
        try {
            UserEntity userEntity = jdbcTemplate.queryForObject(sql, UserRowMapper(), uid);

            return userEntity;
        } catch (EmptyResultDataAccessException e) {
            return new UserEntity();
        }

    }



    @Override
    public Boolean isEmailDuplicateCheck(String uid) {
        String sql = "SELECT uid FROM User WHERE uid = ?";
        try {
            String userEntity = jdbcTemplate.queryForObject(sql, String.class, uid);
            if(!userEntity.isEmpty()) {
                return true;
            }
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return false;
    }

    @Override
    public void updateRoleByUid(String uid, String role) {
        String sql = "UPDATE User SET role = ? WHERE uid = ?";
        jdbcTemplate.update(sql, role, uid);
    }

    @Override
    public void updateLikesByUid(String uid, String likes) {
        String sql = "UPDATE User SET likes = ? WHERE uid = ?";
        jdbcTemplate.update(sql, likes, uid);
    }


    private RowMapper<UserEntity> UserRowMapper() { //테이블 결과의 행과 객체를 반환해주는 rowmapper
        ArrayList<String> roles = new ArrayList<>();
        return (rs, rowNum) -> { //해당 컬럼을 찾아 객체로 매핑
            roles.add(rs.getString("role"));
            UserEntity userEntity = new UserEntity();
            userEntity.setId(rs.getLong("id"));
            userEntity.setUid(rs.getString("uid"));
            userEntity.setPassword(rs.getString("password"));
            userEntity.setName(rs.getString("name"));
            userEntity.setRoles(roles);
            userEntity.setGender(rs.getString("gender"));
            userEntity.setAge(rs.getString("age"));
            userEntity.setBirthday(rs.getString("birthday"));
            userEntity.setAddress(rs.getString("address"));
            userEntity.setLikes(rs.getString("likes"));
            return userEntity;
        };
    }
}
