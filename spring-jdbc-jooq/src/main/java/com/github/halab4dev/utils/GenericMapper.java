package com.github.halab4dev.utils;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface GenericMapper<D, E> {

    // Chuyển từ Domain sang Entity (Dùng cho Insert)
    E toEntity(D domain);

    // Chuyển từ Entity sang Domain (Dùng cho Query/Read)
    D toDomain(E entity);

    // Cập nhật Domain từ Entity (Dùng để gán ngược ID/Audit sau khi Insert)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDomainFromEntity(E entity, @MappingTarget D domain);
}
