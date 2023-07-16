package ingresos.repository;

import ingresos.domain.InfoAcademica;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the InfoAcademica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoAcademicaRepository extends ReactiveCrudRepository<InfoAcademica, Long>, InfoAcademicaRepositoryInternal {
    @Query("SELECT * FROM info_academica entity WHERE entity.academica_id = :id")
    Flux<InfoAcademica> findByAcademica(Long id);

    @Query("SELECT * FROM info_academica entity WHERE entity.academica_id IS NULL")
    Flux<InfoAcademica> findAllWhereAcademicaIsNull();

    @Override
    <S extends InfoAcademica> Mono<S> save(S entity);

    @Override
    Flux<InfoAcademica> findAll();

    @Override
    Mono<InfoAcademica> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface InfoAcademicaRepositoryInternal {
    <S extends InfoAcademica> Mono<S> save(S entity);

    Flux<InfoAcademica> findAllBy(Pageable pageable);

    Flux<InfoAcademica> findAll();

    Mono<InfoAcademica> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<InfoAcademica> findAllBy(Pageable pageable, Criteria criteria);

}
