package ingresos.repository;

import ingresos.domain.InfoBachillerato;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the InfoBachillerato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoBachilleratoRepository extends ReactiveCrudRepository<InfoBachillerato, Long>, InfoBachilleratoRepositoryInternal {
    @Override
    <S extends InfoBachillerato> Mono<S> save(S entity);

    @Override
    Flux<InfoBachillerato> findAll();

    @Override
    Mono<InfoBachillerato> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface InfoBachilleratoRepositoryInternal {
    <S extends InfoBachillerato> Mono<S> save(S entity);

    Flux<InfoBachillerato> findAllBy(Pageable pageable);

    Flux<InfoBachillerato> findAll();

    Mono<InfoBachillerato> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<InfoBachillerato> findAllBy(Pageable pageable, Criteria criteria);

}
