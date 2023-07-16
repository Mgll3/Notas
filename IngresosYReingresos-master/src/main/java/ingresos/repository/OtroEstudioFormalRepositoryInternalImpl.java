package ingresos.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import ingresos.domain.OtroEstudioFormal;
import ingresos.repository.rowmapper.InfoAcademicaRowMapper;
import ingresos.repository.rowmapper.OtroEstudioFormalRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the OtroEstudioFormal entity.
 */
@SuppressWarnings("unused")
class OtroEstudioFormalRepositoryInternalImpl
    extends SimpleR2dbcRepository<OtroEstudioFormal, Long>
    implements OtroEstudioFormalRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final InfoAcademicaRowMapper infoacademicaMapper;
    private final OtroEstudioFormalRowMapper otroestudioformalMapper;

    private static final Table entityTable = Table.aliased("otro_estudio_formal", EntityManager.ENTITY_ALIAS);
    private static final Table otroFormalTable = Table.aliased("info_academica", "otroFormal");

    public OtroEstudioFormalRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        InfoAcademicaRowMapper infoacademicaMapper,
        OtroEstudioFormalRowMapper otroestudioformalMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(OtroEstudioFormal.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.infoacademicaMapper = infoacademicaMapper;
        this.otroestudioformalMapper = otroestudioformalMapper;
    }

    @Override
    public Flux<OtroEstudioFormal> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<OtroEstudioFormal> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = OtroEstudioFormalSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(InfoAcademicaSqlHelper.getColumns(otroFormalTable, "otroFormal"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(otroFormalTable)
            .on(Column.create("otro_formal_id", entityTable))
            .equals(Column.create("id", otroFormalTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, OtroEstudioFormal.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<OtroEstudioFormal> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<OtroEstudioFormal> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private OtroEstudioFormal process(Row row, RowMetadata metadata) {
        OtroEstudioFormal entity = otroestudioformalMapper.apply(row, "e");
        entity.setOtroFormal(infoacademicaMapper.apply(row, "otroFormal"));
        return entity;
    }

    @Override
    public <S extends OtroEstudioFormal> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
