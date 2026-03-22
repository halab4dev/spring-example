package com.github.halab4dev.repository.role;

import com.github.halab4dev.domain.Role;
import com.github.halab4dev.utils.RoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJdbcRepository roleJdbcRepository;

    private final RoleMapper roleMapper;

    @Override
    public void insertMany(Iterable<Role> roles) {
        List<Role> roleList = StreamSupport.stream(roles.spliterator(), false).toList();
        List<RoleEntity> entities = roleList.stream().map(roleMapper::toEntity).toList();

        List<RoleEntity> savedEntities = roleJdbcRepository.saveAll(entities).stream().toList();

        for (int i = 0; i < roleList.size(); i++) {
            roleMapper.updateDomainFromEntity(savedEntities.get(i), roleList.get(i));
        }
    }

    @Override
    public void deleteAll() {
        roleJdbcRepository.deleteAll();
    }
}
