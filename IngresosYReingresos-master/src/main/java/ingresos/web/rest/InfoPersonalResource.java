package ingresos.web.rest;

import ingresos.domain.InfoPersonal;
import ingresos.repository.InfoPersonalRepository;
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
 * REST controller for managing {@link ingresos.domain.InfoPersonal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InfoPersonalResource {

    private final Logger log = LoggerFactory.getLogger(InfoPersonalResource.class);

    private static final String ENTITY_NAME = "modulo3IyRInfoPersonal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoPersonalRepository infoPersonalRepository;

    public InfoPersonalResource(InfoPersonalRepository infoPersonalRepository) {
        this.infoPersonalRepository = infoPersonalRepository;
    }

    /**
     * {@code POST  /info-personals} : Create a new infoPersonal.
     *
     * @param infoPersonal the infoPersonal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoPersonal, or with status {@code 400 (Bad Request)} if the infoPersonal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-personals")
    public Mono<ResponseEntity<InfoPersonal>> createInfoPersonal(@RequestBody InfoPersonal infoPersonal) throws URISyntaxException {
        log.debug("REST request to save InfoPersonal : {}", infoPersonal);
        if (infoPersonal.getId() != null) {
            throw new BadRequestAlertException("A new infoPersonal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return infoPersonalRepository
            .save(infoPersonal)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/info-personals/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /info-personals/:id} : Updates an existing infoPersonal.
     *
     * @param id the id of the infoPersonal to save.
     * @param infoPersonal the infoPersonal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoPersonal,
     * or with status {@code 400 (Bad Request)} if the infoPersonal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoPersonal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-personals/{id}")
    public Mono<ResponseEntity<InfoPersonal>> updateInfoPersonal(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody InfoPersonal infoPersonal
    ) throws URISyntaxException {
        log.debug("REST request to update InfoPersonal : {}, {}", id, infoPersonal);
        if (infoPersonal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoPersonal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return infoPersonalRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return infoPersonalRepository
                    .save(infoPersonal.setIsPersisted())
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /info-personals/:id} : Partial updates given fields of an existing infoPersonal, field will ignore if it is null
     *
     * @param id the id of the infoPersonal to save.
     * @param infoPersonal the infoPersonal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoPersonal,
     * or with status {@code 400 (Bad Request)} if the infoPersonal is not valid,
     * or with status {@code 404 (Not Found)} if the infoPersonal is not found,
     * or with status {@code 500 (Internal Server Error)} if the infoPersonal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/info-personals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<InfoPersonal>> partialUpdateInfoPersonal(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody InfoPersonal infoPersonal
    ) throws URISyntaxException {
        log.debug("REST request to partial update InfoPersonal partially : {}, {}", id, infoPersonal);
        if (infoPersonal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoPersonal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return infoPersonalRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<InfoPersonal> result = infoPersonalRepository
                    .findById(infoPersonal.getId())
                    .map(existingInfoPersonal -> {
                        if (infoPersonal.getTipoID() != null) {
                            existingInfoPersonal.setTipoID(infoPersonal.getTipoID());
                        }
                        if (infoPersonal.getNombre() != null) {
                            existingInfoPersonal.setNombre(infoPersonal.getNombre());
                        }
                        if (infoPersonal.getApellidos() != null) {
                            existingInfoPersonal.setApellidos(infoPersonal.getApellidos());
                        }
                        if (infoPersonal.getGenero() != null) {
                            existingInfoPersonal.setGenero(infoPersonal.getGenero());
                        }
                        if (infoPersonal.getFechaNacimiento() != null) {
                            existingInfoPersonal.setFechaNacimiento(infoPersonal.getFechaNacimiento());
                        }
                        if (infoPersonal.getEdadIngreso() != null) {
                            existingInfoPersonal.setEdadIngreso(infoPersonal.getEdadIngreso());
                        }
                        if (infoPersonal.getTelefono() != null) {
                            existingInfoPersonal.setTelefono(infoPersonal.getTelefono());
                        }
                        if (infoPersonal.getCelular() != null) {
                            existingInfoPersonal.setCelular(infoPersonal.getCelular());
                        }
                        if (infoPersonal.getCorreoPersonal() != null) {
                            existingInfoPersonal.setCorreoPersonal(infoPersonal.getCorreoPersonal());
                        }
                        if (infoPersonal.getNacionalidad() != null) {
                            existingInfoPersonal.setNacionalidad(infoPersonal.getNacionalidad());
                        }

                        return existingInfoPersonal;
                    })
                    .flatMap(infoPersonalRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, res.getId()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /info-personals} : get all the infoPersonals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoPersonals in body.
     */
    @GetMapping("/info-personals")
    public Mono<List<InfoPersonal>> getAllInfoPersonals() {
        log.debug("REST request to get all InfoPersonals");
        return infoPersonalRepository.findAll().collectList();
    }

    /**
     * {@code GET  /info-personals} : get all the infoPersonals as a stream.
     * @return the {@link Flux} of infoPersonals.
     */
    @GetMapping(value = "/info-personals", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<InfoPersonal> getAllInfoPersonalsAsStream() {
        log.debug("REST request to get all InfoPersonals as a stream");
        return infoPersonalRepository.findAll();
    }

    /**
     * {@code GET  /info-personals/:id} : get the "id" infoPersonal.
     *
     * @param id the id of the infoPersonal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoPersonal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-personals/{id}")
    public Mono<ResponseEntity<InfoPersonal>> getInfoPersonal(@PathVariable String id) {
        log.debug("REST request to get InfoPersonal : {}", id);
        Mono<InfoPersonal> infoPersonal = infoPersonalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(infoPersonal);
    }

    /**
     * {@code DELETE  /info-personals/:id} : delete the "id" infoPersonal.
     *
     * @param id the id of the infoPersonal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-personals/{id}")
    public Mono<ResponseEntity<Void>> deleteInfoPersonal(@PathVariable String id) {
        log.debug("REST request to delete InfoPersonal : {}", id);
        return infoPersonalRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
                        .build()
                )
            );
    }
}
