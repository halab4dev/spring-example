package com.github.halab4dev.repository.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.halab4dev.domain.Role;
import com.github.halab4dev.domain.User;
import com.github.halab4dev.repository.BaseRepositoryImpl;
import com.github.halab4dev.repository.entity.BaseEntity;
import com.github.halab4dev.repository.role.RoleEntity;
import com.github.halab4dev.repository.userrole.UserRoleEntity;
import com.github.halab4dev.utils.UserMapper;
import org.jooq.DSLContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User, UserEntity, String> implements UserRepository {

    private static final String USERS = UserEntity.TABLE_NAME;
    private static final String USERS_ID = USERS + "." + BaseEntity.ID;
    private static final String USERS_NAME = USERS + "." + UserEntity.NAME;
    private static final String USERS_NICK_NAMES = USERS + "." + UserEntity.NICK_NAMES;

    private static final String USER_ROLES = UserRoleEntity.TABLE_NAME;
    private static final String USER_ROLES_USER_ID = USER_ROLES + "." + UserRoleEntity.USER_ID;
    private static final String USER_ROLES_ROLE_ID = USER_ROLES + "." + UserRoleEntity.ROLE_ID;

    private static final String ROLES = RoleEntity.TABLE_NAME;
    private static final String ROLES_ID = ROLES + "." + BaseEntity.ID;
    private static final String ROLES_NAME = ROLES + "." + RoleEntity.NAME;

    private final UserJDBCRepository jdbcRepository;
    private final UserMapper mapper;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected UserRepositoryImpl(
            UserJDBCRepository jdbcRepository,
            JdbcTemplate jdbcTemplate,
            DSLContext dslContext, UserMapper mapper,
            AuditorAware<String> auditorAware
    ) {
        super(jdbcRepository, jdbcTemplate, dslContext, mapper, UserEntity.TABLE_NAME, auditorAware);
        this.jdbcRepository = jdbcRepository;
        this.mapper = mapper;
    }

    @Override
    protected Class<UserEntity> getEntityType() {
        return UserEntity.class;
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = jdbcRepository.findAll();
        return userEntities.stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public User findByIdWithRoles(String id) {
        return this.dslContext
                .select(
                        field(USERS_ID),
                        field(USERS_NAME),
                        field(USERS_NICK_NAMES),
                        multiset(
                                select(field(ROLES_ID), field(ROLES_NAME))
                                        .from(ROLES)
                                        .join(USER_ROLES).on(field(USER_ROLES_ROLE_ID).eq(field(ROLES_ID)))
                                        .where(field(USER_ROLES_USER_ID).eq(USERS_ID))
                        ).as("roles").convertFrom(r -> r.map(record ->
                                new Role(record.get(ROLES_ID, String.class), record.get(ROLES_NAME, String.class)))
                        )
                )
                .from(USERS)
                .where(field(USERS_ID).eq(id))
                .fetchOne(record -> {

                    User user = new User();
                    user.setId(record.get(USERS_ID, String.class));
                    user.setName(record.get(USERS_NAME, String.class));

                    try {
                        @SuppressWarnings("unchecked")
                        List<String> nickNames = (List<String>) objectMapper.readValue(record.get(USERS_NICK_NAMES, String.class), List.class);
                        user.setNickNames(nickNames);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        var rolesJson = record.get("roles", String.class);
                        List<Role> roles = (List<Role>) objectMapper.readValue(rolesJson, List.class);
                        user.setRoles(roles);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    return user;
                });
    }

    @Override
    public void update(User user) {
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put(UserEntity.NAME, user.getName());
        update(user.getId(), updateFields);
    }

    @Override
    public void deleteAll() {
        jdbcRepository.deleteAll();
    }
}
