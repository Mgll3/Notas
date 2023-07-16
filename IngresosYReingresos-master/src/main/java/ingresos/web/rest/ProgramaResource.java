package ingresos.web.rest;

import ingresos.domain.Programa;
import ingresos.repository.ProgramaRepository;
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
 * REST controller for managing {@link ingresos.domain.Programa}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProgramaResource {

    private final Logger log = LoggerFactory.getLogger(ProgramaResource.class);

    private static final String ENTITY_NAME = "modulo3IyRPrograma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramaRepository programaRepository;

    public ProgramaResource(ProgramaRepository programaRepository) {
        this.programaRepository = programaRepository;
    }

    /**
     * {@code POST  /programas} : Create a new programa.
     *
     * @param programa the programa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programa, or with status {@code 400 (Bad Request)} if the programa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/programas")
    public Mono<ResponseEntity<Programa>> createPrograma(@RequestBody Programa programa) throws URISyntaxException {
        log.debug("REST request to save Programa : {}", programa);
        if (programa.getId() != null) {
            throw new BadRequestAlertException("A new programa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return programaRepository
            .save(programa)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/programas/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /programas/:id} : Updates an existing programa.
     *
     * @param id the id of the programa to save.
     * @param programa the programa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programa,
     * or with status {@code 400 (Bad Request)} if the programa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/programas/{id}")
    public Mono<ResponseEntity<Programa>> updatePrograma(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Programa programa
    ) throws URISyntaxException {
        log.debug("REST request to update Programa : {}, {}", id, programa);
        if (programa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return programaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return programaRepository
                    .save(programa)
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
     * {@code PATCH  /programas/:id} : Partial updates given fields of an existing programa, field will ignore if it is null
     *
     * @param id the id of the programa to save.
     * @param programa the programa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programa,
     * or with status {@code 400 (Bad Request)} if the programa is not valid,
     * or with status {@code 404 (Not Found)} if the programa is not found,
     * or with status {@code 500 (Internal Server Error)} if the programa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/programas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Programa>> partialUpdatePrograma(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Programa programa
    ) throws URISyntaxException {
        log.debug("REST request to partial update Programa partially : {}, {}", id, programa);
        if (programa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return programaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Programa> result = programaRepository
                    .findById(programa.getId())
                    .map(existingPrograma -> {
                        if (programa.getCodigo() != null) {
                            existingPrograma.setCodigo(programa.getCodigo());
                        }
                        if (programa.getNombre() != null) {
                            existingPrograma.setNombre(programa.getNombre());
                        }
                        if (programa.getRestriccion() != null) {
                            existingPrograma.setRestriccion(programa.getRestriccion());
                        }
                        if (programa.getDescripcion() != null) {
                            existingPrograma.setDescripcion(programa.getDescripcion());
                        }

                        return existingPrograma;
                    })
                    .flatMap(programaRepository::save);

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
     * {@code GET  /programas} : get all the programas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programas in body.
     */
    @GetMapping("/programas")
    public Mono<List<Programa>> getAllProgramas() {
        log.debug("REST request to get all Programas");
        return programaRepository.findAll().collectList();
    }

    /**
     * {@code GET  /programas} : get all the programas as a stream.
     * @return the {@link Flux} of programas.
     */
    @GetMapping(value = "/programas", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Programa> getAllProgramasAsStream() {
        log.debug("REST request to get all Programas as a stream");
        return programaRepository.findAll();
    }

    /**
     * {@code GET  /programas/:id} : get the "id" programa.
     *
     * @param id the id of the programa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/programas/{id}")
    public Mono<ResponseEntity<Programa>> getPrograma(@PathVariable Long id) {
        log.debug("REST request to get Programa : {}", id);
        Mono<Programa> programa = programaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(programa);
    }

    /**
     * {@code DELETE  /programas/:id} : delete the "id" programa.
     *
     * @param id the id of the programa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/programas/{id}")
    public Mono<ResponseEntity<Void>> deletePrograma(@PathVariable Long id) {
        log.debug("REST request to delete Programa : {}", id);
        return programaRepository
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
