package com.github.halab4dev.repository;

import com.github.halab4dev.domain.BaseDomain;
import com.github.halab4dev.repository.entity.BaseEntity;
import com.github.halab4dev.repository.entity.SoftDeletedEntity;
import com.github.halab4dev.utils.GenericMapper;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public abstract class BaseRepositoryImpl<D extends BaseDomain<ID>, E extends BaseEntity, ID> implements BaseRepository<D, E, ID> {

    protected final ListCrudRepository<E, ID> jdbcRepository;
    protected final JdbcTemplate jdbcTemplate;
    protected final DSLContext dslContext;
    protected final GenericMapper<D, E> mapper;
    protected final Table<?> table;
    protected final AuditorAware<String> auditorAware;

    protected BaseRepositoryImpl(
            ListCrudRepository<E, ID> jdbcRepository,
            JdbcTemplate jdbcTemplate,
            DSLContext dslContext,
            GenericMapper<D, E> mapper,
            String tableName,
            AuditorAware<String> auditorAware
    ) {
        this.jdbcRepository = jdbcRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.dslContext = dslContext;
        this.mapper = mapper;
        this.table = table(tableName);
        this.auditorAware = auditorAware;
    }

    protected abstract Class<E> getEntityType();

    @Override
    public void insert(D domain) {
        E entity = mapper.toEntity(domain);
        E saved = jdbcRepository.save(entity);
        mapper.updateDomainFromEntity(saved, domain); // Set id, audit back to entity
    }

    @Override
    public void insertMany(Iterable<D> domains) {
        List<D> domainList = StreamSupport.stream(domains.spliterator(), false).toList();
        List<E> entities = domainList.stream().map(mapper::toEntity).collect(Collectors.toList());
        List<E> savedEntities = jdbcRepository.saveAll(entities).stream().toList();

        for (int i = 0; i < domainList.size(); i++) {
            mapper.updateDomainFromEntity(savedEntities.get(i), domainList.get(i));  // Set id, audit back to entity
        }
    }

    @Override
    public void update(ID id, Map<String, Object> updatedFields) {
        var updateSetStep = dslContext.update(table)
                .set(field(BaseEntity.LAST_MODIFIED_BY), getActor())
                .set(field(BaseEntity.LAST_MODIFIED_TIME), LocalDateTime.now());

        Set<Map.Entry<String, Object>> mapEntries = updatedFields.entrySet();
        for (var entry : mapEntries) {
            String columnName = entry.getKey();
            Object value = entry.getValue();
            updateSetStep = updateSetStep.set(field(columnName), value);
        }

        var query = updateSetStep.where(field(BaseEntity.ID).eq(id));

        var sql = query.getSQL();
        Object[] bindValues = query.getBindValues().toArray();

        jdbcTemplate.update(sql, bindValues);
    }

    @Override
    public void delete(D domain) {
        if (SoftDeletedEntity.class.isAssignableFrom(getEntityType())) {
            dslContext.update(table)
                    .set(field(SoftDeletedEntity.IS_DELETED), true)
                    .set(field(BaseEntity.LAST_MODIFIED_BY), getActor())
                    .set(field(BaseEntity.LAST_MODIFIED_TIME), LocalDateTime.now())
                    .where(field(BaseEntity.ID).eq(domain.getId()))
                    .execute();
        } else {
           jdbcRepository.deleteById(domain.getId());
        }
    }

    private String getActor() {
        return auditorAware.getCurrentAuditor().orElse("System");
    }
}
