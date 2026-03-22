package com.github.halab4dev.repository.userrole;

import com.github.halab4dev.repository.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(UserRoleEntity.TABLE_NAME)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRoleEntity extends BaseEntity {

    public static final String TABLE_NAME = "user_roles";

    public static final String USER_ID = "user_id";
    public static final String ROLE_ID = "role_id";


    private String userId;
    private String roleId;

    public UserRoleEntity(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
