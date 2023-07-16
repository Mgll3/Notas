package ingresos.repository;

import ingresos.domain.Programa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Programa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramaRepository extends ReactiveCrudRepository<Programa, Long>, ProgramaRepositoryInternal {
    @Override
    <S extends Programa> Mono<S> save(S entity);

    @Override
    Flux<Programa> findAll();

    @Override
    Mono<Programa> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ProgramaRepositoryInternal {
    <S extends Programa> Mono<S> save(S entity);

    Flux<Programa> findAllBy(Pageable pageable);

    Flux<Programa> findAll();

    Mono<Programa> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Programa> findAllBy(Pageable pageable, Criteria criteria);

}
