package ingresos.web.rest;

import ingresos.domain.OtroEstudioFormal;
import ingresos.repository.OtroEstudioFormalRepository;
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
 * REST controller for managing {@link ingresos.domain.OtroEstudioFormal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OtroEstudioFormalResource {

    private final Logger log = LoggerFactory.getLogger(OtroEstudioFormalResource.class);

    private static final String ENTITY_NAME = "modulo3IyROtroEstudioFormal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OtroEstudioFormalRepository otroEstudioFormalRepository;

    public OtroEstudioFormalResource(OtroEstudioFormalRepository otroEstudioFormalRepository) {
        this.otroEstudioFormalRepository = otroEstudioFormalRepository;
    }

    /**
     * {@code POST  /otro-estudio-formals} : Create a new otroEstudioFormal.
     *
     * @param otroEstudioFormal the otroEstudioFormal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new otroEstudioFormal, or with status {@code 400 (Bad Request)} if the otroEstudioFormal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/otro-estudio-formals")
    public Mono<ResponseEntity<OtroEstudioFormal>> createOtroEstudioFormal(@RequestBody OtroEstudioFormal otroEstudioFormal)
        throws URISyntaxException {
        log.debug("REST request to save OtroEstudioFormal : {}", otroEstudioFormal);
        if (otroEstudioFormal.getId() != null) {
            throw new BadRequestAlertException("A new otroEstudioFormal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return otroEstudioFormalRepository
            .save(otroEstudioFormal)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/otro-estudio-formals/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /otro-estudio-formals/:id} : Updates an existing otroEstudioFormal.
     *
     * @param id the id of the otroEstudioFormal to save.
     * @param otroEstudioFormal the otroEstudioFormal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otroEstudioFormal,
     * or with status {@code 400 (Bad Request)} if the otroEstudioFormal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the otroEstudioFormal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/otro-estudio-formals/{id}")
    public Mono<ResponseEntity<OtroEstudioFormal>> updateOtroEstudioFormal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OtroEstudioFormal otroEstudioFormal
    ) throws URISyntaxException {
        log.debug("REST request to update OtroEstudioFormal : {}, {}", id, otroEstudioFormal);
        if (otroEstudioFormal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otroEstudioFormal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return otroEstudioFormalRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return otroEstudioFormalRepository
                    .save(otroEstudioFormal)
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
     * {@code PATCH  /otro-estudio-formals/:id} : Partial updates given fields of an existing otroEstudioFormal, field will ignore if it is null
     *
     * @param id the id of the otroEstudioFormal to save.
     * @param otroEstudioFormal the otroEstudioFormal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otroEstudioFormal,
     * or with status {@code 400 (Bad Request)} if the otroEstudioFormal is not valid,
     * or with status {@code 404 (Not Found)} if the otroEstudioFormal is not found,
     * or with status {@code 500 (Internal Server Error)} if the otroEstudioFormal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/otro-estudio-formals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<OtroEstudioFormal>> partialUpdateOtroEstudioFormal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OtroEstudioFormal otroEstudioFormal
    ) throws URISyntaxException {
        log.debug("REST request to partial update OtroEstudioFormal partially : {}, {}", id, otroEstudioFormal);
        if (otroEstudioFormal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otroEstudioFormal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return otroEstudioFormalRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<OtroEstudioFormal> result = otroEstudioFormalRepository
                    .findById(otroEstudioFormal.getId())
                    .map(existingOtroEstudioFormal -> {
                        if (otroEstudioFormal.getInstitucion() != null) {
                            existingOtroEstudioFormal.setInstitucion(otroEstudioFormal.getInstitucion());
                        }
                        if (otroEstudioFormal.getEstudio() != null) {
                            existingOtroEstudioFormal.setEstudio(otroEstudioFormal.getEstudio());
                        }
                        if (otroEstudioFormal.getGraduado() != null) {
                            existingOtroEstudioFormal.setGraduado(otroEstudioFormal.getGraduado());
                        }
                        if (otroEstudioFormal.getYear() != null) {
                            existingOtroEstudioFormal.setYear(otroEstudioFormal.getYear());
                        }
                        if (otroEstudioFormal.getTituloObtenido() != null) {
                            existingOtroEstudioFormal.setTituloObtenido(otroEstudioFormal.getTituloObtenido());
                        }
                        if (otroEstudioFormal.getNivel() != null) {
                            existingOtroEstudioFormal.setNivel(otroEstudioFormal.getNivel());
                        }

                        return existingOtroEstudioFormal;
                    })
                    .flatMap(otroEstudioFormalRepository::save);

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
     * {@code GET  /otro-estudio-formals} : get all the otroEstudioFormals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of otroEstudioFormals in body.
     */
    @GetMapping("/otro-estudio-formals")
    public Mono<List<OtroEstudioFormal>> getAllOtroEstudioFormals() {
        log.debug("REST request to get all OtroEstudioFormals");
        return otroEstudioFormalRepository.findAll().collectList();
    }

    /**
     * {@code GET  /otro-estudio-formals} : get all the otroEstudioFormals as a stream.
     * @return the {@link Flux} of otroEstudioFormals.
     */
    @GetMapping(value = "/otro-estudio-formals", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<OtroEstudioFormal> getAllOtroEstudioFormalsAsStream() {
        log.debug("REST request to get all OtroEstudioFormals as a stream");
        return otroEstudioFormalRepository.findAll();
    }

    /**
     * {@code GET  /otro-estudio-formals/:id} : get the "id" otroEstudioFormal.
     *
     * @param id the id of the otroEstudioFormal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the otroEstudioFormal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/otro-estudio-formals/{id}")
    public Mono<ResponseEntity<OtroEstudioFormal>> getOtroEstudioFormal(@PathVariable Long id) {
        log.debug("REST request to get OtroEstudioFormal : {}", id);
        Mono<OtroEstudioFormal> otroEstudioFormal = otroEstudioFormalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(otroEstudioFormal);
    }

    /**
     * {@code DELETE  /otro-estudio-formals/:id} : delete the "id" otroEstudioFormal.
     *
     * @param id the id of the otroEstudioFormal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/otro-estudio-formals/{id}")
    public Mono<ResponseEntity<Void>> deleteOtroEstudioFormal(@PathVariable Long id) {
        log.debug("REST request to delete OtroEstudioFormal : {}", id);
        return otroEstudioFormalRepository
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
