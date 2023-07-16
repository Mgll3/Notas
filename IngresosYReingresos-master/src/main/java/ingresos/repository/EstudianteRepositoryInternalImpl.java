package ingresos.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import ingresos.domain.Estudiante;
import ingresos.repository.rowmapper.EstudianteRowMapper;
import ingresos.repository.rowmapper.InfoAcademicaRowMapper;
import ingresos.repository.rowmapper.InfoEconomicaRowMapper;
import ingresos.repository.rowmapper.InfoPersonalRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Estudiante entity.
 */
@SuppressWarnings("unused")
class EstudianteRepositoryInternalImpl extends SimpleR2dbcRepository<Estudiante, Long> implements EstudianteRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final InfoPersonalRowMapper infopersonalMapper;
    private final InfoEconomicaRowMapper infoeconomicaMapper;
    private final InfoAcademicaRowMapper infoacademicaMapper;
    private final EstudianteRowMapper estudianteMapper;

    private static final Table entityTable = Table.aliased("estudiante", EntityManager.ENTITY_ALIAS);
    private static final Table estudiantePTable = Table.aliased("info_personal", "estudianteP");
    private static final Table estudianteETable = Table.aliased("info_economica", "estudianteE");
    private static final Table estudianteATable = Table.aliased("info_academica", "estudianteA");

    public EstudianteRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        InfoPersonalRowMapper infopersonalMapper,
        InfoEconomicaRowMapper infoeconomicaMapper,
        InfoAcademicaRowMapper infoacademicaMapper,
        EstudianteRowMapper estudianteMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Estudiante.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.infopersonalMapper = infopersonalMapper;
        this.infoeconomicaMapper = infoeconomicaMapper;
        this.infoacademicaMapper = infoacademicaMapper;
        this.estudianteMapper = estudianteMapper;
    }

    @Override
    public Flux<Estudiante> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Estudiante> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = EstudianteSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(InfoPersonalSqlHelper.getColumns(estudiantePTable, "estudianteP"));
        columns.addAll(InfoEconomicaSqlHelper.getColumns(estudianteETable, "estudianteE"));
        columns.addAll(InfoAcademicaSqlHelper.getColumns(estudianteATable, "estudianteA"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(estudiantePTable)
            .on(Column.create("estudiantep_id", entityTable))
            .equals(Column.create("id", estudiantePTable))
            .leftOuterJoin(estudianteETable)
            .on(Column.create("estudiantee_id", entityTable))
            .equals(Column.create("id", estudianteETable))
            .leftOuterJoin(estudianteATable)
            .on(Column.create("estudiantea_id", entityTable))
            .equals(Column.create("id", estudianteATable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Estudiante.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Estudiante> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Estudiante> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Estudiante process(Row row, RowMetadata metadata) {
        Estudiante entity = estudianteMapper.apply(row, "e");
        entity.setEstudianteP(infopersonalMapper.apply(row, "estudianteP"));
        entity.setEstudianteE(infoeconomicaMapper.apply(row, "estudianteE"));
        entity.setEstudianteA(infoacademicaMapper.apply(row, "estudianteA"));
        return entity;
    }

    @Override
    public <S extends Estudiante> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
