package ingresos.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import ingresos.domain.InfoAcademica;
import ingresos.repository.rowmapper.InfoAcademicaRowMapper;
import ingresos.repository.rowmapper.InfoBachilleratoRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the InfoAcademica entity.
 */
@SuppressWarnings("unused")
class InfoAcademicaRepositoryInternalImpl extends SimpleR2dbcRepository<InfoAcademica, Long> implements InfoAcademicaRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final InfoBachilleratoRowMapper infobachilleratoMapper;
    private final InfoAcademicaRowMapper infoacademicaMapper;

    private static final Table entityTable = Table.aliased("info_academica", EntityManager.ENTITY_ALIAS);
    private static final Table academicaTable = Table.aliased("info_bachillerato", "academica");

    public InfoAcademicaRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        InfoBachilleratoRowMapper infobachilleratoMapper,
        InfoAcademicaRowMapper infoacademicaMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(InfoAcademica.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.infobachilleratoMapper = infobachilleratoMapper;
        this.infoacademicaMapper = infoacademicaMapper;
    }

    @Override
    public Flux<InfoAcademica> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<InfoAcademica> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = InfoAcademicaSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(InfoBachilleratoSqlHelper.getColumns(academicaTable, "academica"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(academicaTable)
            .on(Column.create("academica_id", entityTable))
            .equals(Column.create("id", academicaTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, InfoAcademica.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<InfoAcademica> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<InfoAcademica> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private InfoAcademica process(Row row, RowMetadata metadata) {
        InfoAcademica entity = infoacademicaMapper.apply(row, "e");
        entity.setAcademica(infobachilleratoMapper.apply(row, "academica"));
        return entity;
    }

    @Override
    public <S extends InfoAcademica> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
