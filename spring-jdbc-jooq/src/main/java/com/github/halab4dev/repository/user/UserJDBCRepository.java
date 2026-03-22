package com.github.halab4dev.repository.user;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface UserJDBCRepository extends ListCrudRepository<UserEntity, String> {

    @Override
    @Query("""
        SELECT *
        FROM users
        WHERE is_deleted = false
    """)
    List<UserEntity> findAll();
}
