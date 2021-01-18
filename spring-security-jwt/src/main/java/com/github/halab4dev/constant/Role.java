package com.github.halab4dev.constant;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/*
 *
 * @author halab
 */
@Data
@AllArgsConstructor
public class Role {

    private String name;
    private List<Privilege> privileges;
}
