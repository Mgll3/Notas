package ingresos.web.rest;

import ingresos.domain.InfoAcademica;
import ingresos.repository.InfoAcademicaRepository;
import ingresos.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link ingresos.domain.InfoAcademica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InfoAcademicaResource {

    private final Logger log = LoggerFactory.getLogger(InfoAcademicaResource.class);

    private static final String ENTITY_NAME = "modulo3IyRInfoAcademica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoAcademicaRepository infoAcademicaRepository;

    public InfoAcademicaResource(InfoAcademicaRepository infoAcademicaRepository) {
        this.infoAcademicaRepository = infoAcademicaRepository;
    }

    /**
     * {@code POST  /info-academicas} : Create a new infoAcademica.
     *
     * @param infoAcademica the infoAcademica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoAcademica, or with status {@code 400 (Bad Request)} if the infoAcademica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-academicas")
    public Mono<ResponseEntity<InfoAcademica>> createInfoAcademica(@Valid @RequestBody InfoAcademica infoAcademica)
        throws URISyntaxException {
        log.debug("REST request to save InfoAcademica : {}", infoAcademica);
        if (infoAcademica.getId() != null) {
            throw new BadRequestAlertException("A new infoAcademica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return infoAcademicaRepository
            .save(infoAcademica)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/info-academicas/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /info-academicas/:id} : Updates an existing infoAcademica.
     *
     * @param id the id of the infoAcademica to save.
     * @param infoAcademica the infoAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoAcademica,
     * or with status {@code 400 (Bad Request)} if the infoAcademica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-academicas/{id}")
    public Mono<ResponseEntity<InfoAcademica>> updateInfoAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InfoAcademica infoAcademica
    ) throws URISyntaxException {
        log.debug("REST request to update InfoAcademica : {}, {}", id, infoAcademica);
        if (infoAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return infoAcademicaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return infoAcademicaRepository
                    .save(infoAcademica)
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
     * {@code PATCH  /info-academicas/:id} : Partial updates given fields of an existing infoAcademica, field will ignore if it is null
     *
     * @param id the id of the infoAcademica to save.
     * @param infoAcademica the infoAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoAcademica,
     * or with status {@code 400 (Bad Request)} if the infoAcademica is not valid,
     * or with status {@code 404 (Not Found)} if the infoAcademica is not found,
     * or with status {@code 500 (Internal Server Error)} if the infoAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/info-academicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<InfoAcademica>> partialUpdateInfoAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InfoAcademica infoAcademica
    ) throws URISyntaxException {
        log.debug("REST request to partial update InfoAcademica partially : {}, {}", id, infoAcademica);
        if (infoAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return infoAcademicaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<InfoAcademica> result = infoAcademicaRepository
                    .findById(infoAcademica.getId())
                    .map(existingInfoAcademica -> {
                        return existingInfoAcademica;
                    })
                    .flatMap(infoAcademicaRepository::save);

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
     * {@code GET  /info-academicas} : get all the infoAcademicas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoAcademicas in body.
     */
    @GetMapping("/info-academicas")
    public Mono<List<InfoAcademica>> getAllInfoAcademicas() {
        log.debug("REST request to get all InfoAcademicas");
        return infoAcademicaRepository.findAll().collectList();
    }

    /**
     * {@code GET  /info-academicas} : get all the infoAcademicas as a stream.
     * @return the {@link Flux} of infoAcademicas.
     */
    @GetMapping(value = "/info-academicas", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<InfoAcademica> getAllInfoAcademicasAsStream() {
        log.debug("REST request to get all InfoAcademicas as a stream");
        return infoAcademicaRepository.findAll();
    }

    /**
     * {@code GET  /info-academicas/:id} : get the "id" infoAcademica.
     *
     * @param id the id of the infoAcademica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoAcademica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-academicas/{id}")
    public Mono<ResponseEntity<InfoAcademica>> getInfoAcademica(@PathVariable Long id) {
        log.debug("REST request to get InfoAcademica : {}", id);
        Mono<InfoAcademica> infoAcademica = infoAcademicaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(infoAcademica);
    }

    /**
     * {@code DELETE  /info-academicas/:id} : delete the "id" infoAcademica.
     *
     * @param id the id of the infoAcademica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-academicas/{id}")
    public Mono<ResponseEntity<Void>> deleteInfoAcademica(@PathVariable Long id) {
        log.debug("REST request to delete InfoAcademica : {}", id);
        return infoAcademicaRepository
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
