package com.github.halab4dev.repository.userrole;

import com.github.halab4dev.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository {

    private final UserRoleJdbcRepository userRoleJdbcRepository;

    @Override
    public void saveUserRole(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return;
        }
        List<UserRoleEntity> userRoles = user.getRoles().stream()
                .map(role -> new UserRoleEntity(user.getId(), role.getId()))
                .toList();
        userRoleJdbcRepository.saveAll(userRoles);
    }

    @Override
    public void deleteAll() {
        userRoleJdbcRepository.deleteAll();
    }
}
