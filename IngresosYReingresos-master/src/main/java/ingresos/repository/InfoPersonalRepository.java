package ingresos.repository;

import ingresos.domain.InfoPersonal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the InfoPersonal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoPersonalRepository extends ReactiveCrudRepository<InfoPersonal, String>, InfoPersonalRepositoryInternal {
    @Override
    <S extends InfoPersonal> Mono<S> save(S entity);

    @Override
    Flux<InfoPersonal> findAll();

    @Override
    Mono<InfoPersonal> findById(String id);

    @Override
    Mono<Void> deleteById(String id);
}

interface InfoPersonalRepositoryInternal {
    <S extends InfoPersonal> Mono<S> save(S entity);

    Flux<InfoPersonal> findAllBy(Pageable pageable);

    Flux<InfoPersonal> findAll();

    Mono<InfoPersonal> findById(String id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<InfoPersonal> findAllBy(Pageable pageable, Criteria criteria);

}
