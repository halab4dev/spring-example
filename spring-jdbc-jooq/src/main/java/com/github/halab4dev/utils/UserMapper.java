package com.github.halab4dev.utils;

import com.github.halab4dev.domain.User;
import com.github.halab4dev.repository.entity.StringList;
import com.github.halab4dev.repository.user.UserEntity;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring") // Let MapStruct create Bean for Spring
public interface UserMapper extends GenericMapper<User, UserEntity> {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);

    default List<String> map(StringList nickNames) {
        return nickNames == null ? Collections.emptyList() : nickNames.values();
    }

    default StringList map(List<String> nickNames) {
        return new StringList(nickNames);
    }
}