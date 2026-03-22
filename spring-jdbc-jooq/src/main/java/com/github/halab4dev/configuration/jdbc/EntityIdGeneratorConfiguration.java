package com.github.halab4dev.configuration.jdbc;

import com.github.halab4dev.repository.entity.BaseEntity;
import com.github.halab4dev.utils.UuidGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

@Configuration
public class EntityIdGeneratorConfiguration {

    @Bean
    public BeforeConvertCallback<BaseEntity> idGeneration() {
        return entity -> {
            if (entity.getId() == null) {
                entity.setId(UuidGenerator.newV7Uuid());
            }
            return entity;
        };
    }

}
