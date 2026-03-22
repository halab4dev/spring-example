package com.github.halab4dev.repository.entity;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {

    public static final String ID = "id";

    public static final String CREATED_BY = "created_by";
    public static final String CREATED_TIME = "created_time";
    public static final String LAST_MODIFIED_BY = "last_modified_by";
    public static final String LAST_MODIFIED_TIME = "last_modified_time";

    @Id
    @Column(ID)
    private String id;

    @CreatedBy
    @Column(CREATED_BY)
    private String createdBy;

    @CreatedDate
    @Column(CREATED_TIME)
    private LocalDateTime createdTime;

    @LastModifiedBy
    @Column(LAST_MODIFIED_BY)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(LAST_MODIFIED_TIME)
    private LocalDateTime lastModifiedTime;

    /*
    Can implement Persistable<String> to prevent JDBC check existed by query a SELECT before INSERT OR UPDATE


    @Transient // Not save to DB
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNotNew() {
        this.isNew = false;
    }
    */

}
