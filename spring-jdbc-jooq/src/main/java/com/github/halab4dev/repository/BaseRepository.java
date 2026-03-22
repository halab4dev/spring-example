package com.github.halab4dev.repository;

import com.github.halab4dev.domain.BaseDomain;
import com.github.halab4dev.repository.entity.BaseEntity;

import java.util.Map;

public interface BaseRepository<D extends BaseDomain<ID>, E extends BaseEntity, ID> {

    void insert(D domain);

    void insertMany(Iterable<D> domains);

    void update(ID id, Map<String, Object> updatedFields);

    void delete(D domain);
}
