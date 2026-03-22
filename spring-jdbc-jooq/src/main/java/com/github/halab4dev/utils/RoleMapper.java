package com.github.halab4dev.utils;

import com.github.halab4dev.domain.Role;
import com.github.halab4dev.domain.User;
import com.github.halab4dev.repository.entity.StringList;
import com.github.halab4dev.repository.role.RoleEntity;
import com.github.halab4dev.repository.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring") // Let MapStruct create Bean for Spring
public interface RoleMapper {

    Role toDomain(RoleEntity entity);

    RoleEntity toEntity(Role domain);

    void updateDomainFromEntity(RoleEntity entity, @MappingTarget Role domain);
}