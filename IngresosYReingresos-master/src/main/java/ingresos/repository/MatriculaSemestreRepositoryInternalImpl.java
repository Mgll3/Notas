package ingresos.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import ingresos.domain.MatriculaSemestre;
import ingresos.repository.rowmapper.InfoAcademicaRowMapper;
import ingresos.repository.rowmapper.MatriculaSemestreRowMapper;
import ingresos.repository.rowmapper.ProgramaRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the MatriculaSemestre entity.
 */
@SuppressWarnings("unused")
class MatriculaSemestreRepositoryInternalImpl
    extends SimpleR2dbcRepository<MatriculaSemestre, Long>
    implements MatriculaSemestreRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ProgramaRowMapper programaMapper;
    private final InfoAcademicaRowMapper infoacademicaMapper;
    private final MatriculaSemestreRowMapper matriculasemestreMapper;

    private static final Table entityTable = Table.aliased("matricula_semestre", EntityManager.ENTITY_ALIAS);
    private static final Table matriculaSTable = Table.aliased("programa", "matriculaS");
    private static final Table matriculaATable = Table.aliased("info_academica", "matriculaA");

    public MatriculaSemestreRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ProgramaRowMapper programaMapper,
        InfoAcademicaRowMapper infoacademicaMapper,
        MatriculaSemestreRowMapper matriculasemestreMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(MatriculaSemestre.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.programaMapper = programaMapper;
        this.infoacademicaMapper = infoacademicaMapper;
        this.matriculasemestreMapper = matriculasemestreMapper;
    }

    @Override
    public Flux<MatriculaSemestre> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<MatriculaSemestre> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = MatriculaSemestreSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(ProgramaSqlHelper.getColumns(matriculaSTable, "matriculaS"));
        columns.addAll(InfoAcademicaSqlHelper.getColumns(matriculaATable, "matriculaA"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(matriculaSTable)
            .on(Column.create("matriculas_id", entityTable))
            .equals(Column.create("id", matriculaSTable))
            .leftOuterJoin(matriculaATable)
            .on(Column.create("matriculaa_id", entityTable))
            .equals(Column.create("id", matriculaATable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, MatriculaSemestre.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<MatriculaSemestre> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<MatriculaSemestre> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private MatriculaSemestre process(Row row, RowMetadata metadata) {
        MatriculaSemestre entity = matriculasemestreMapper.apply(row, "e");
        entity.setMatriculaS(programaMapper.apply(row, "matriculaS"));
        entity.setMatriculaA(infoacademicaMapper.apply(row, "matriculaA"));
        return entity;
    }

    @Override
    public <S extends MatriculaSemestre> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
