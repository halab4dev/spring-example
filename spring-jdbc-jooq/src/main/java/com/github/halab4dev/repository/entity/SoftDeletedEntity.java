package com.github.halab4dev.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class SoftDeletedEntity extends BaseEntity{

    public static final String IS_DELETED = "is_deleted";

    @Column(IS_DELETED)
    private boolean isDeleted;
}
