package com.github.halab4dev.repository.user;

import com.github.halab4dev.repository.entity.SoftDeletedEntity;
import com.github.halab4dev.repository.entity.StringList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = UserEntity.TABLE_NAME)
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends SoftDeletedEntity {

    static final String TABLE_NAME = "users";

    static final String NAME = "name";
    static final String NICK_NAMES = "nick_names";

    @Column(NAME)
    private String name;

    @Column(NICK_NAMES)
    private StringList nickNames; // use wrapper class because jdbc not support List directly
}
