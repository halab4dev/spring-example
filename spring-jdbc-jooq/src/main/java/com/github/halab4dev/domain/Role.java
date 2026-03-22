package com.github.halab4dev.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseDomain<String> {

    private String name;

    public Role(String id, String name) {
        setId(id);
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
    }
}
