package ingresos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import ingresos.IntegrationTest;
import ingresos.domain.InfoAcademica;
import ingresos.domain.InfoBachillerato;
import ingresos.repository.EntityManager;
import ingresos.repository.InfoAcademicaRepository;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link InfoAcademicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class InfoAcademicaResourceIT {

    private static final String ENTITY_API_URL = "/api/info-academicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InfoAcademicaRepository infoAcademicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private InfoAcademica infoAcademica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoAcademica createEntity(EntityManager em) {
        InfoAcademica infoAcademica = new InfoAcademica();
        // Add required entity
        InfoBachillerato infoBachillerato;
        infoBachillerato = em.insert(InfoBachilleratoResourceIT.createEntity(em)).block();
        infoAcademica.setAcademica(infoBachillerato);
        return infoAcademica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoAcademica createUpdatedEntity(EntityManager em) {
        InfoAcademica infoAcademica = new InfoAcademica();
        // Add required entity
        InfoBachillerato infoBachillerato;
        infoBachillerato = em.insert(InfoBachilleratoResourceIT.createUpdatedEntity(em)).block();
        infoAcademica.setAcademica(infoBachillerato);
        return infoAcademica;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(InfoAcademica.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        InfoBachilleratoResourceIT.deleteEntities(em);
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        infoAcademica = createEntity(em);
    }

    @Test
    void createInfoAcademica() throws Exception {
        int databaseSizeBeforeCreate = infoAcademicaRepository.findAll().collectList().block().size();
        // Create the InfoAcademica
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoAcademica))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeCreate + 1);
        InfoAcademica testInfoAcademica = infoAcademicaList.get(infoAcademicaList.size() - 1);
    }

    @Test
    void createInfoAcademicaWithExistingId() throws Exception {
        // Create the InfoAcademica with an existing ID
        infoAcademica.setId(1L);

        int databaseSizeBeforeCreate = infoAcademicaRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoAcademica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllInfoAcademicasAsStream() {
        // Initialize the database
        infoAcademicaRepository.save(infoAcademica).block();

        List<InfoAcademica> infoAcademicaList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(InfoAcademica.class)
            .getResponseBody()
            .filter(infoAcademica::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(infoAcademicaList).isNotNull();
        assertThat(infoAcademicaList).hasSize(1);
        InfoAcademica testInfoAcademica = infoAcademicaList.get(0);
    }

    @Test
    void getAllInfoAcademicas() {
        // Initialize the database
        infoAcademicaRepository.save(infoAcademica).block();

        // Get all the infoAcademicaList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(infoAcademica.getId().intValue()));
    }

    @Test
    void getInfoAcademica() {
        // Initialize the database
        infoAcademicaRepository.save(infoAcademica).block();

        // Get the infoAcademica
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, infoAcademica.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(infoAcademica.getId().intValue()));
    }

    @Test
    void getNonExistingInfoAcademica() {
        // Get the infoAcademica
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingInfoAcademica() throws Exception {
        // Initialize the database
        infoAcademicaRepository.save(infoAcademica).block();

        int databaseSizeBeforeUpdate = infoAcademicaRepository.findAll().collectList().block().size();

        // Update the infoAcademica
        InfoAcademica updatedInfoAcademica = infoAcademicaRepository.findById(infoAcademica.getId()).block();

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedInfoAcademica.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedInfoAcademica))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeUpdate);
        InfoAcademica testInfoAcademica = infoAcademicaList.get(infoAcademicaList.size() - 1);
    }

    @Test
    void putNonExistingInfoAcademica() throws Exception {
        int databaseSizeBeforeUpdate = infoAcademicaRepository.findAll().collectList().block().size();
        infoAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, infoAcademica.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoAcademica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchInfoAcademica() throws Exception {
        int databaseSizeBeforeUpdate = infoAcademicaRepository.findAll().collectList().block().size();
        infoAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoAcademica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamInfoAcademica() throws Exception {
        int databaseSizeBeforeUpdate = infoAcademicaRepository.findAll().collectList().block().size();
        infoAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoAcademica))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateInfoAcademicaWithPatch() throws Exception {
        // Initialize the database
        infoAcademicaRepository.save(infoAcademica).block();

        int databaseSizeBeforeUpdate = infoAcademicaRepository.findAll().collectList().block().size();

        // Update the infoAcademica using partial update
        InfoAcademica partialUpdatedInfoAcademica = new InfoAcademica();
        partialUpdatedInfoAcademica.setId(infoAcademica.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedInfoAcademica.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoAcademica))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeUpdate);
        InfoAcademica testInfoAcademica = infoAcademicaList.get(infoAcademicaList.size() - 1);
    }

    @Test
    void fullUpdateInfoAcademicaWithPatch() throws Exception {
        // Initialize the database
        infoAcademicaRepository.save(infoAcademica).block();

        int databaseSizeBeforeUpdate = infoAcademicaRepository.findAll().collectList().block().size();

        // Update the infoAcademica using partial update
        InfoAcademica partialUpdatedInfoAcademica = new InfoAcademica();
        partialUpdatedInfoAcademica.setId(infoAcademica.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedInfoAcademica.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoAcademica))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeUpdate);
        InfoAcademica testInfoAcademica = infoAcademicaList.get(infoAcademicaList.size() - 1);
    }

    @Test
    void patchNonExistingInfoAcademica() throws Exception {
        int databaseSizeBeforeUpdate = infoAcademicaRepository.findAll().collectList().block().size();
        infoAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, infoAcademica.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoAcademica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchInfoAcademica() throws Exception {
        int databaseSizeBeforeUpdate = infoAcademicaRepository.findAll().collectList().block().size();
        infoAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoAcademica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamInfoAcademica() throws Exception {
        int databaseSizeBeforeUpdate = infoAcademicaRepository.findAll().collectList().block().size();
        infoAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoAcademica))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the InfoAcademica in the database
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteInfoAcademica() {
        // Initialize the database
        infoAcademicaRepository.save(infoAcademica).block();

        int databaseSizeBeforeDelete = infoAcademicaRepository.findAll().collectList().block().size();

        // Delete the infoAcademica
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, infoAcademica.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<InfoAcademica> infoAcademicaList = infoAcademicaRepository.findAll().collectList().block();
        assertThat(infoAcademicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
