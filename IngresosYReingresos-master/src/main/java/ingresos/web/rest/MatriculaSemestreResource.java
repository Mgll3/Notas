package ingresos.web.rest;

import ingresos.domain.MatriculaSemestre;
import ingresos.repository.MatriculaSemestreRepository;
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
 * REST controller for managing {@link ingresos.domain.MatriculaSemestre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MatriculaSemestreResource {

    private final Logger log = LoggerFactory.getLogger(MatriculaSemestreResource.class);

    private static final String ENTITY_NAME = "modulo3IyRMatriculaSemestre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatriculaSemestreRepository matriculaSemestreRepository;

    public MatriculaSemestreResource(MatriculaSemestreRepository matriculaSemestreRepository) {
        this.matriculaSemestreRepository = matriculaSemestreRepository;
    }

    /**
     * {@code POST  /matricula-semestres} : Create a new matriculaSemestre.
     *
     * @param matriculaSemestre the matriculaSemestre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matriculaSemestre, or with status {@code 400 (Bad Request)} if the matriculaSemestre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matricula-semestres")
    public Mono<ResponseEntity<MatriculaSemestre>> createMatriculaSemestre(@Valid @RequestBody MatriculaSemestre matriculaSemestre)
        throws URISyntaxException {
        log.debug("REST request to save MatriculaSemestre : {}", matriculaSemestre);
        if (matriculaSemestre.getId() != null) {
            throw new BadRequestAlertException("A new matriculaSemestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return matriculaSemestreRepository
            .save(matriculaSemestre)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/matricula-semestres/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /matricula-semestres/:id} : Updates an existing matriculaSemestre.
     *
     * @param id the id of the matriculaSemestre to save.
     * @param matriculaSemestre the matriculaSemestre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matriculaSemestre,
     * or with status {@code 400 (Bad Request)} if the matriculaSemestre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matriculaSemestre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matricula-semestres/{id}")
    public Mono<ResponseEntity<MatriculaSemestre>> updateMatriculaSemestre(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MatriculaSemestre matriculaSemestre
    ) throws URISyntaxException {
        log.debug("REST request to update MatriculaSemestre : {}, {}", id, matriculaSemestre);
        if (matriculaSemestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matriculaSemestre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return matriculaSemestreRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return matriculaSemestreRepository
                    .save(matriculaSemestre)
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
     * {@code PATCH  /matricula-semestres/:id} : Partial updates given fields of an existing matriculaSemestre, field will ignore if it is null
     *
     * @param id the id of the matriculaSemestre to save.
     * @param matriculaSemestre the matriculaSemestre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matriculaSemestre,
     * or with status {@code 400 (Bad Request)} if the matriculaSemestre is not valid,
     * or with status {@code 404 (Not Found)} if the matriculaSemestre is not found,
     * or with status {@code 500 (Internal Server Error)} if the matriculaSemestre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/matricula-semestres/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<MatriculaSemestre>> partialUpdateMatriculaSemestre(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MatriculaSemestre matriculaSemestre
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatriculaSemestre partially : {}, {}", id, matriculaSemestre);
        if (matriculaSemestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matriculaSemestre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return matriculaSemestreRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<MatriculaSemestre> result = matriculaSemestreRepository
                    .findById(matriculaSemestre.getId())
                    .map(existingMatriculaSemestre -> {
                        if (matriculaSemestre.getTipoIngreso() != null) {
                            existingMatriculaSemestre.setTipoIngreso(matriculaSemestre.getTipoIngreso());
                        }
                        if (matriculaSemestre.getTipoAspirante() != null) {
                            existingMatriculaSemestre.setTipoAspirante(matriculaSemestre.getTipoAspirante());
                        }
                        if (matriculaSemestre.getVersion() != null) {
                            existingMatriculaSemestre.setVersion(matriculaSemestre.getVersion());
                        }
                        if (matriculaSemestre.getModalidad() != null) {
                            existingMatriculaSemestre.setModalidad(matriculaSemestre.getModalidad());
                        }
                        if (matriculaSemestre.getSede() != null) {
                            existingMatriculaSemestre.setSede(matriculaSemestre.getSede());
                        }
                        if (matriculaSemestre.getYear() != null) {
                            existingMatriculaSemestre.setYear(matriculaSemestre.getYear());
                        }
                        if (matriculaSemestre.getCohorte() != null) {
                            existingMatriculaSemestre.setCohorte(matriculaSemestre.getCohorte());
                        }
                        if (matriculaSemestre.getEstado() != null) {
                            existingMatriculaSemestre.setEstado(matriculaSemestre.getEstado());
                        }
                        if (matriculaSemestre.getPromedioSemestre() != null) {
                            existingMatriculaSemestre.setPromedioSemestre(matriculaSemestre.getPromedioSemestre());
                        }
                        if (matriculaSemestre.getPromedioAcumulado() != null) {
                            existingMatriculaSemestre.setPromedioAcumulado(matriculaSemestre.getPromedioAcumulado());
                        }
                        if (matriculaSemestre.getLiquidacionMatricula() != null) {
                            existingMatriculaSemestre.setLiquidacionMatricula(matriculaSemestre.getLiquidacionMatricula());
                        }
                        if (matriculaSemestre.getEstadoPago() != null) {
                            existingMatriculaSemestre.setEstadoPago(matriculaSemestre.getEstadoPago());
                        }
                        if (matriculaSemestre.getSituacionAcademica() != null) {
                            existingMatriculaSemestre.setSituacionAcademica(matriculaSemestre.getSituacionAcademica());
                        }

                        return existingMatriculaSemestre;
                    })
                    .flatMap(matriculaSemestreRepository::save);

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
     * {@code GET  /matricula-semestres} : get all the matriculaSemestres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matriculaSemestres in body.
     */
    @GetMapping("/matricula-semestres")
    public Mono<List<MatriculaSemestre>> getAllMatriculaSemestres() {
        log.debug("REST request to get all MatriculaSemestres");
        return matriculaSemestreRepository.findAll().collectList();
    }

    /**
     * {@code GET  /matricula-semestres} : get all the matriculaSemestres as a stream.
     * @return the {@link Flux} of matriculaSemestres.
     */
    @GetMapping(value = "/matricula-semestres", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MatriculaSemestre> getAllMatriculaSemestresAsStream() {
        log.debug("REST request to get all MatriculaSemestres as a stream");
        return matriculaSemestreRepository.findAll();
    }

    /**
     * {@code GET  /matricula-semestres/:id} : get the "id" matriculaSemestre.
     *
     * @param id the id of the matriculaSemestre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matriculaSemestre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matricula-semestres/{id}")
    public Mono<ResponseEntity<MatriculaSemestre>> getMatriculaSemestre(@PathVariable Long id) {
        log.debug("REST request to get MatriculaSemestre : {}", id);
        Mono<MatriculaSemestre> matriculaSemestre = matriculaSemestreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(matriculaSemestre);
    }

    /**
     * {@code DELETE  /matricula-semestres/:id} : delete the "id" matriculaSemestre.
     *
     * @param id the id of the matriculaSemestre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matricula-semestres/{id}")
    public Mono<ResponseEntity<Void>> deleteMatriculaSemestre(@PathVariable Long id) {
        log.debug("REST request to delete MatriculaSemestre : {}", id);
        return matriculaSemestreRepository
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
