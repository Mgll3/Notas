package ingresos.repository;

import ingresos.domain.OtroEstudioFormal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the OtroEstudioFormal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtroEstudioFormalRepository extends ReactiveCrudRepository<OtroEstudioFormal, Long>, OtroEstudioFormalRepositoryInternal {
    @Query("SELECT * FROM otro_estudio_formal entity WHERE entity.otro_formal_id = :id")
    Flux<OtroEstudioFormal> findByOtroFormal(Long id);

    @Query("SELECT * FROM otro_estudio_formal entity WHERE entity.otro_formal_id IS NULL")
    Flux<OtroEstudioFormal> findAllWhereOtroFormalIsNull();

    @Override
    <S extends OtroEstudioFormal> Mono<S> save(S entity);

    @Override
    Flux<OtroEstudioFormal> findAll();

    @Override
    Mono<OtroEstudioFormal> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface OtroEstudioFormalRepositoryInternal {
    <S extends OtroEstudioFormal> Mono<S> save(S entity);

    Flux<OtroEstudioFormal> findAllBy(Pageable pageable);

    Flux<OtroEstudioFormal> findAll();

    Mono<OtroEstudioFormal> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<OtroEstudioFormal> findAllBy(Pageable pageable, Criteria criteria);

}
