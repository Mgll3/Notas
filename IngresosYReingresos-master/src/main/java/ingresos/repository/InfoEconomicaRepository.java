package ingresos.repository;

import ingresos.domain.InfoEconomica;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the InfoEconomica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoEconomicaRepository extends ReactiveCrudRepository<InfoEconomica, Long>, InfoEconomicaRepositoryInternal {
    @Override
    <S extends InfoEconomica> Mono<S> save(S entity);

    @Override
    Flux<InfoEconomica> findAll();

    @Override
    Mono<InfoEconomica> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface InfoEconomicaRepositoryInternal {
    <S extends InfoEconomica> Mono<S> save(S entity);

    Flux<InfoEconomica> findAllBy(Pageable pageable);

    Flux<InfoEconomica> findAll();

    Mono<InfoEconomica> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<InfoEconomica> findAllBy(Pageable pageable, Criteria criteria);

}
