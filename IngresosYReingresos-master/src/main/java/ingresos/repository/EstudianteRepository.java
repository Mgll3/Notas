package ingresos.repository;

import ingresos.models.Estudiante;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Estudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteRepository extends ReactiveCrudRepository<Estudiante, Long>, EstudianteRepositoryInternal {
    @Query("SELECT * FROM estudiante entity WHERE entity.estudiantep_id = :id")
    Flux<Estudiante> findByEstudianteP(Long id);

    @Query("SELECT * FROM estudiante entity WHERE entity.estudiantep_id IS NULL")
    Flux<Estudiante> findAllWhereEstudiantePIsNull();

    @Query("SELECT * FROM estudiante entity WHERE entity.estudiantee_id = :id")
    Flux<Estudiante> findByEstudianteE(Long id);

    @Query("SELECT * FROM estudiante entity WHERE entity.estudiantee_id IS NULL")
    Flux<Estudiante> findAllWhereEstudianteEIsNull();

    @Query("SELECT * FROM estudiante entity WHERE entity.estudiantea_id = :id")
    Flux<Estudiante> findByEstudianteA(Long id);

    @Query("SELECT * FROM estudiante entity WHERE entity.estudiantea_id IS NULL")
    Flux<Estudiante> findAllWhereEstudianteAIsNull();

    @Override
    <S extends Estudiante> Mono<S> save(S entity);

    @Override
    Flux<Estudiante> findAll();

    @Override
    Mono<Estudiante> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface EstudianteRepositoryInternal {
    <S extends Estudiante> Mono<S> save(S entity);

    Flux<Estudiante> findAllBy(Pageable pageable);

    Flux<Estudiante> findAll();

    Mono<Estudiante> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Estudiante> findAllBy(Pageable pageable, Criteria criteria);

}
