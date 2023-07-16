package ingresos.web.rest;

import ingresos.domain.InfoBachillerato;
import ingresos.repository.InfoBachilleratoRepository;
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
 * REST controller for managing {@link ingresos.domain.InfoBachillerato}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InfoBachilleratoResource {

    private final Logger log = LoggerFactory.getLogger(InfoBachilleratoResource.class);

    private static final String ENTITY_NAME = "modulo3IyRInfoBachillerato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoBachilleratoRepository infoBachilleratoRepository;

    public InfoBachilleratoResource(InfoBachilleratoRepository infoBachilleratoRepository) {
        this.infoBachilleratoRepository = infoBachilleratoRepository;
    }

    /**
     * {@code POST  /info-bachilleratoes} : Create a new infoBachillerato.
     *
     * @param infoBachillerato the infoBachillerato to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoBachillerato, or with status {@code 400 (Bad Request)} if the infoBachillerato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-bachilleratoes")
    public Mono<ResponseEntity<InfoBachillerato>> createInfoBachillerato(@RequestBody InfoBachillerato infoBachillerato)
        throws URISyntaxException {
        log.debug("REST request to save InfoBachillerato : {}", infoBachillerato);
        if (infoBachillerato.getId() != null) {
            throw new BadRequestAlertException("A new infoBachillerato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return infoBachilleratoRepository
            .save(infoBachillerato)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/info-bachilleratoes/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /info-bachilleratoes/:id} : Updates an existing infoBachillerato.
     *
     * @param id the id of the infoBachillerato to save.
     * @param infoBachillerato the infoBachillerato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoBachillerato,
     * or with status {@code 400 (Bad Request)} if the infoBachillerato is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoBachillerato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-bachilleratoes/{id}")
    public Mono<ResponseEntity<InfoBachillerato>> updateInfoBachillerato(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InfoBachillerato infoBachillerato
    ) throws URISyntaxException {
        log.debug("REST request to update InfoBachillerato : {}, {}", id, infoBachillerato);
        if (infoBachillerato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoBachillerato.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return infoBachilleratoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return infoBachilleratoRepository
                    .save(infoBachillerato)
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
     * {@code PATCH  /info-bachilleratoes/:id} : Partial updates given fields of an existing infoBachillerato, field will ignore if it is null
     *
     * @param id the id of the infoBachillerato to save.
     * @param infoBachillerato the infoBachillerato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoBachillerato,
     * or with status {@code 400 (Bad Request)} if the infoBachillerato is not valid,
     * or with status {@code 404 (Not Found)} if the infoBachillerato is not found,
     * or with status {@code 500 (Internal Server Error)} if the infoBachillerato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/info-bachilleratoes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<InfoBachillerato>> partialUpdateInfoBachillerato(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InfoBachillerato infoBachillerato
    ) throws URISyntaxException {
        log.debug("REST request to partial update InfoBachillerato partially : {}, {}", id, infoBachillerato);
        if (infoBachillerato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoBachillerato.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return infoBachilleratoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<InfoBachillerato> result = infoBachilleratoRepository
                    .findById(infoBachillerato.getId())
                    .map(existingInfoBachillerato -> {
                        if (infoBachillerato.getCiudad() != null) {
                            existingInfoBachillerato.setCiudad(infoBachillerato.getCiudad());
                        }
                        if (infoBachillerato.getDepartamento() != null) {
                            existingInfoBachillerato.setDepartamento(infoBachillerato.getDepartamento());
                        }
                        if (infoBachillerato.getPais() != null) {
                            existingInfoBachillerato.setPais(infoBachillerato.getPais());
                        }
                        if (infoBachillerato.getColegio() != null) {
                            existingInfoBachillerato.setColegio(infoBachillerato.getColegio());
                        }
                        if (infoBachillerato.getYear() != null) {
                            existingInfoBachillerato.setYear(infoBachillerato.getYear());
                        }
                        if (infoBachillerato.getModalidad() != null) {
                            existingInfoBachillerato.setModalidad(infoBachillerato.getModalidad());
                        }
                        if (infoBachillerato.getPuntajeIcfes() != null) {
                            existingInfoBachillerato.setPuntajeIcfes(infoBachillerato.getPuntajeIcfes());
                        }

                        return existingInfoBachillerato;
                    })
                    .flatMap(infoBachilleratoRepository::save);

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
     * {@code GET  /info-bachilleratoes} : get all the infoBachilleratoes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoBachilleratoes in body.
     */
    @GetMapping("/info-bachilleratoes")
    public Mono<List<InfoBachillerato>> getAllInfoBachilleratoes() {
        log.debug("REST request to get all InfoBachilleratoes");
        return infoBachilleratoRepository.findAll().collectList();
    }

    /**
     * {@code GET  /info-bachilleratoes} : get all the infoBachilleratoes as a stream.
     * @return the {@link Flux} of infoBachilleratoes.
     */
    @GetMapping(value = "/info-bachilleratoes", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<InfoBachillerato> getAllInfoBachilleratoesAsStream() {
        log.debug("REST request to get all InfoBachilleratoes as a stream");
        return infoBachilleratoRepository.findAll();
    }

    /**
     * {@code GET  /info-bachilleratoes/:id} : get the "id" infoBachillerato.
     *
     * @param id the id of the infoBachillerato to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoBachillerato, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-bachilleratoes/{id}")
    public Mono<ResponseEntity<InfoBachillerato>> getInfoBachillerato(@PathVariable Long id) {
        log.debug("REST request to get InfoBachillerato : {}", id);
        Mono<InfoBachillerato> infoBachillerato = infoBachilleratoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(infoBachillerato);
    }

    /**
     * {@code DELETE  /info-bachilleratoes/:id} : delete the "id" infoBachillerato.
     *
     * @param id the id of the infoBachillerato to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-bachilleratoes/{id}")
    public Mono<ResponseEntity<Void>> deleteInfoBachillerato(@PathVariable Long id) {
        log.debug("REST request to delete InfoBachillerato : {}", id);
        return infoBachilleratoRepository
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
