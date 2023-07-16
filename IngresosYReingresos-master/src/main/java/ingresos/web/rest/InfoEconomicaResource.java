package ingresos.web.rest;

import ingresos.domain.InfoEconomica;
import ingresos.repository.InfoEconomicaRepository;
import ingresos.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link ingresos.domain.InfoEconomica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InfoEconomicaResource {

    private final Logger log = LoggerFactory.getLogger(InfoEconomicaResource.class);

    private static final String ENTITY_NAME = "modulo3IyRInfoEconomica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoEconomicaRepository infoEconomicaRepository;

    public InfoEconomicaResource(InfoEconomicaRepository infoEconomicaRepository) {
        this.infoEconomicaRepository = infoEconomicaRepository;
    }

    /**
     * {@code POST  /info-economicas} : Create a new infoEconomica.
     *
     * @param infoEconomica the infoEconomica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoEconomica, or with status {@code 400 (Bad Request)} if the infoEconomica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-economicas")
    public Mono<ResponseEntity<InfoEconomica>> createInfoEconomica(@RequestBody InfoEconomica infoEconomica) throws URISyntaxException {
        log.debug("REST request to save InfoEconomica : {}", infoEconomica);
        if (infoEconomica.getId() != null) {
            throw new BadRequestAlertException("A new infoEconomica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return infoEconomicaRepository
            .save(infoEconomica)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/info-economicas/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /info-economicas/:id} : Updates an existing infoEconomica.
     *
     * @param id the id of the infoEconomica to save.
     * @param infoEconomica the infoEconomica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoEconomica,
     * or with status {@code 400 (Bad Request)} if the infoEconomica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoEconomica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-economicas/{id}")
    public Mono<ResponseEntity<InfoEconomica>> updateInfoEconomica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InfoEconomica infoEconomica
    ) throws URISyntaxException {
        log.debug("REST request to update InfoEconomica : {}, {}", id, infoEconomica);
        if (infoEconomica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoEconomica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return infoEconomicaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return infoEconomicaRepository
                    .save(infoEconomica)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /info-economicas/:id} : Partial updates given fields of an existing infoEconomica, field will ignore if it is null
     *
     * @param id the id of the infoEconomica to save.
     * @param infoEconomica the infoEconomica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoEconomica,
     * or with status {@code 400 (Bad Request)} if the infoEconomica is not valid,
     * or with status {@code 404 (Not Found)} if the infoEconomica is not found,
     * or with status {@code 500 (Internal Server Error)} if the infoEconomica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/info-economicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<InfoEconomica>> partialUpdateInfoEconomica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InfoEconomica infoEconomica
    ) throws URISyntaxException {
        log.debug("REST request to partial update InfoEconomica partially : {}, {}", id, infoEconomica);
        if (infoEconomica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoEconomica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return infoEconomicaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<InfoEconomica> result = infoEconomicaRepository
                    .findById(infoEconomica.getId())
                    .map(existingInfoEconomica -> {
                        if (infoEconomica.getDireccion() != null) {
                            existingInfoEconomica.setDireccion(infoEconomica.getDireccion());
                        }
                        if (infoEconomica.getBarrio() != null) {
                            existingInfoEconomica.setBarrio(infoEconomica.getBarrio());
                        }
                        if (infoEconomica.getCiudad() != null) {
                            existingInfoEconomica.setCiudad(infoEconomica.getCiudad());
                        }
                        if (infoEconomica.getDepartmento() != null) {
                            existingInfoEconomica.setDepartmento(infoEconomica.getDepartmento());
                        }
                        if (infoEconomica.getEstrato() != null) {
                            existingInfoEconomica.setEstrato(infoEconomica.getEstrato());
                        }
                        if (infoEconomica.getDependeEcono() != null) {
                            existingInfoEconomica.setDependeEcono(infoEconomica.getDependeEcono());
                        }
                        if (infoEconomica.getTrabaja() != null) {
                            existingInfoEconomica.setTrabaja(infoEconomica.getTrabaja());
                        }
                        if (infoEconomica.getTieneDependientes() != null) {
                            existingInfoEconomica.setTieneDependientes(infoEconomica.getTieneDependientes());
                        }

                        return existingInfoEconomica;
                    })
                    .flatMap(infoEconomicaRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /info-economicas} : get all the infoEconomicas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoEconomicas in body.
     */
    @GetMapping("/info-economicas")
    public Mono<List<InfoEconomica>> getAllInfoEconomicas() {
        log.debug("REST request to get all InfoEconomicas");
        return infoEconomicaRepository.findAll().collectList();
    }

    /**
     * {@code GET  /info-economicas} : get all the infoEconomicas as a stream.
     * @return the {@link Flux} of infoEconomicas.
     */
    @GetMapping(value = "/info-economicas", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<InfoEconomica> getAllInfoEconomicasAsStream() {
        log.debug("REST request to get all InfoEconomicas as a stream");
        return infoEconomicaRepository.findAll();
    }

    /**
     * {@code GET  /info-economicas/:id} : get the "id" infoEconomica.
     *
     * @param id the id of the infoEconomica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoEconomica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-economicas/{id}")
    public Mono<ResponseEntity<InfoEconomica>> getInfoEconomica(@PathVariable Long id) {
        log.debug("REST request to get InfoEconomica : {}", id);
        Mono<InfoEconomica> infoEconomica = infoEconomicaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(infoEconomica);
    }

    /**
     * {@code DELETE  /info-economicas/:id} : delete the "id" infoEconomica.
     *
     * @param id the id of the infoEconomica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-economicas/{id}")
    public Mono<ResponseEntity<Void>> deleteInfoEconomica(@PathVariable Long id) {
        log.debug("REST request to delete InfoEconomica : {}", id);
        return infoEconomicaRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
