package com.github.halab4dev.repository.role;

import com.github.halab4dev.repository.entity.BaseEntity;
import com.github.halab4dev.repository.entity.SoftDeletedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(RoleEntity.TABLE_NAME)
@EqualsAndHashCode(callSuper = false)
public class RoleEntity  extends SoftDeletedEntity {

    public static final String TABLE_NAME = "roles";

    public static final String NAME = "name";

    @Column(NAME)
    private String name;
}
