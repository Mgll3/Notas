package ingresos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import ingresos.IntegrationTest;
import ingresos.domain.OtroEstudioFormal;
import ingresos.repository.EntityManager;
import ingresos.repository.OtroEstudioFormalRepository;
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
 * Integration tests for the {@link OtroEstudioFormalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class OtroEstudioFormalResourceIT {

    private static final String DEFAULT_INSTITUCION = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUCION = "BBBBBBBBBB";

    private static final Long DEFAULT_ESTUDIO = 1L;
    private static final Long UPDATED_ESTUDIO = 2L;

    private static final Boolean DEFAULT_GRADUADO = false;
    private static final Boolean UPDATED_GRADUADO = true;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_TITULO_OBTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_OBTENIDO = "BBBBBBBBBB";

    private static final String DEFAULT_NIVEL = "AAAAAAAAAA";
    private static final String UPDATED_NIVEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/otro-estudio-formals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OtroEstudioFormalRepository otroEstudioFormalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private OtroEstudioFormal otroEstudioFormal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtroEstudioFormal createEntity(EntityManager em) {
        OtroEstudioFormal otroEstudioFormal = new OtroEstudioFormal()
            .institucion(DEFAULT_INSTITUCION)
            .estudio(DEFAULT_ESTUDIO)
            .graduado(DEFAULT_GRADUADO)
            .year(DEFAULT_YEAR)
            .tituloObtenido(DEFAULT_TITULO_OBTENIDO)
            .nivel(DEFAULT_NIVEL);
        return otroEstudioFormal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtroEstudioFormal createUpdatedEntity(EntityManager em) {
        OtroEstudioFormal otroEstudioFormal = new OtroEstudioFormal()
            .institucion(UPDATED_INSTITUCION)
            .estudio(UPDATED_ESTUDIO)
            .graduado(UPDATED_GRADUADO)
            .year(UPDATED_YEAR)
            .tituloObtenido(UPDATED_TITULO_OBTENIDO)
            .nivel(UPDATED_NIVEL);
        return otroEstudioFormal;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(OtroEstudioFormal.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
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
        otroEstudioFormal = createEntity(em);
    }

    @Test
    void createOtroEstudioFormal() throws Exception {
        int databaseSizeBeforeCreate = otroEstudioFormalRepository.findAll().collectList().block().size();
        // Create the OtroEstudioFormal
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(otroEstudioFormal))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeCreate + 1);
        OtroEstudioFormal testOtroEstudioFormal = otroEstudioFormalList.get(otroEstudioFormalList.size() - 1);
        assertThat(testOtroEstudioFormal.getInstitucion()).isEqualTo(DEFAULT_INSTITUCION);
        assertThat(testOtroEstudioFormal.getEstudio()).isEqualTo(DEFAULT_ESTUDIO);
        assertThat(testOtroEstudioFormal.getGraduado()).isEqualTo(DEFAULT_GRADUADO);
        assertThat(testOtroEstudioFormal.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testOtroEstudioFormal.getTituloObtenido()).isEqualTo(DEFAULT_TITULO_OBTENIDO);
        assertThat(testOtroEstudioFormal.getNivel()).isEqualTo(DEFAULT_NIVEL);
    }

    @Test
    void createOtroEstudioFormalWithExistingId() throws Exception {
        // Create the OtroEstudioFormal with an existing ID
        otroEstudioFormal.setId(1L);

        int databaseSizeBeforeCreate = otroEstudioFormalRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(otroEstudioFormal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllOtroEstudioFormalsAsStream() {
        // Initialize the database
        otroEstudioFormalRepository.save(otroEstudioFormal).block();

        List<OtroEstudioFormal> otroEstudioFormalList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(OtroEstudioFormal.class)
            .getResponseBody()
            .filter(otroEstudioFormal::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(otroEstudioFormalList).isNotNull();
        assertThat(otroEstudioFormalList).hasSize(1);
        OtroEstudioFormal testOtroEstudioFormal = otroEstudioFormalList.get(0);
        assertThat(testOtroEstudioFormal.getInstitucion()).isEqualTo(DEFAULT_INSTITUCION);
        assertThat(testOtroEstudioFormal.getEstudio()).isEqualTo(DEFAULT_ESTUDIO);
        assertThat(testOtroEstudioFormal.getGraduado()).isEqualTo(DEFAULT_GRADUADO);
        assertThat(testOtroEstudioFormal.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testOtroEstudioFormal.getTituloObtenido()).isEqualTo(DEFAULT_TITULO_OBTENIDO);
        assertThat(testOtroEstudioFormal.getNivel()).isEqualTo(DEFAULT_NIVEL);
    }

    @Test
    void getAllOtroEstudioFormals() {
        // Initialize the database
        otroEstudioFormalRepository.save(otroEstudioFormal).block();

        // Get all the otroEstudioFormalList
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
            .value(hasItem(otroEstudioFormal.getId().intValue()))
            .jsonPath("$.[*].institucion")
            .value(hasItem(DEFAULT_INSTITUCION))
            .jsonPath("$.[*].estudio")
            .value(hasItem(DEFAULT_ESTUDIO.intValue()))
            .jsonPath("$.[*].graduado")
            .value(hasItem(DEFAULT_GRADUADO.booleanValue()))
            .jsonPath("$.[*].year")
            .value(hasItem(DEFAULT_YEAR))
            .jsonPath("$.[*].tituloObtenido")
            .value(hasItem(DEFAULT_TITULO_OBTENIDO))
            .jsonPath("$.[*].nivel")
            .value(hasItem(DEFAULT_NIVEL));
    }

    @Test
    void getOtroEstudioFormal() {
        // Initialize the database
        otroEstudioFormalRepository.save(otroEstudioFormal).block();

        // Get the otroEstudioFormal
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, otroEstudioFormal.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(otroEstudioFormal.getId().intValue()))
            .jsonPath("$.institucion")
            .value(is(DEFAULT_INSTITUCION))
            .jsonPath("$.estudio")
            .value(is(DEFAULT_ESTUDIO.intValue()))
            .jsonPath("$.graduado")
            .value(is(DEFAULT_GRADUADO.booleanValue()))
            .jsonPath("$.year")
            .value(is(DEFAULT_YEAR))
            .jsonPath("$.tituloObtenido")
            .value(is(DEFAULT_TITULO_OBTENIDO))
            .jsonPath("$.nivel")
            .value(is(DEFAULT_NIVEL));
    }

    @Test
    void getNonExistingOtroEstudioFormal() {
        // Get the otroEstudioFormal
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingOtroEstudioFormal() throws Exception {
        // Initialize the database
        otroEstudioFormalRepository.save(otroEstudioFormal).block();

        int databaseSizeBeforeUpdate = otroEstudioFormalRepository.findAll().collectList().block().size();

        // Update the otroEstudioFormal
        OtroEstudioFormal updatedOtroEstudioFormal = otroEstudioFormalRepository.findById(otroEstudioFormal.getId()).block();
        updatedOtroEstudioFormal
            .institucion(UPDATED_INSTITUCION)
            .estudio(UPDATED_ESTUDIO)
            .graduado(UPDATED_GRADUADO)
            .year(UPDATED_YEAR)
            .tituloObtenido(UPDATED_TITULO_OBTENIDO)
            .nivel(UPDATED_NIVEL);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedOtroEstudioFormal.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedOtroEstudioFormal))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeUpdate);
        OtroEstudioFormal testOtroEstudioFormal = otroEstudioFormalList.get(otroEstudioFormalList.size() - 1);
        assertThat(testOtroEstudioFormal.getInstitucion()).isEqualTo(UPDATED_INSTITUCION);
        assertThat(testOtroEstudioFormal.getEstudio()).isEqualTo(UPDATED_ESTUDIO);
        assertThat(testOtroEstudioFormal.getGraduado()).isEqualTo(UPDATED_GRADUADO);
        assertThat(testOtroEstudioFormal.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testOtroEstudioFormal.getTituloObtenido()).isEqualTo(UPDATED_TITULO_OBTENIDO);
        assertThat(testOtroEstudioFormal.getNivel()).isEqualTo(UPDATED_NIVEL);
    }

    @Test
    void putNonExistingOtroEstudioFormal() throws Exception {
        int databaseSizeBeforeUpdate = otroEstudioFormalRepository.findAll().collectList().block().size();
        otroEstudioFormal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, otroEstudioFormal.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(otroEstudioFormal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOtroEstudioFormal() throws Exception {
        int databaseSizeBeforeUpdate = otroEstudioFormalRepository.findAll().collectList().block().size();
        otroEstudioFormal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(otroEstudioFormal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOtroEstudioFormal() throws Exception {
        int databaseSizeBeforeUpdate = otroEstudioFormalRepository.findAll().collectList().block().size();
        otroEstudioFormal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(otroEstudioFormal))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOtroEstudioFormalWithPatch() throws Exception {
        // Initialize the database
        otroEstudioFormalRepository.save(otroEstudioFormal).block();

        int databaseSizeBeforeUpdate = otroEstudioFormalRepository.findAll().collectList().block().size();

        // Update the otroEstudioFormal using partial update
        OtroEstudioFormal partialUpdatedOtroEstudioFormal = new OtroEstudioFormal();
        partialUpdatedOtroEstudioFormal.setId(otroEstudioFormal.getId());

        partialUpdatedOtroEstudioFormal.institucion(UPDATED_INSTITUCION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedOtroEstudioFormal.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedOtroEstudioFormal))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeUpdate);
        OtroEstudioFormal testOtroEstudioFormal = otroEstudioFormalList.get(otroEstudioFormalList.size() - 1);
        assertThat(testOtroEstudioFormal.getInstitucion()).isEqualTo(UPDATED_INSTITUCION);
        assertThat(testOtroEstudioFormal.getEstudio()).isEqualTo(DEFAULT_ESTUDIO);
        assertThat(testOtroEstudioFormal.getGraduado()).isEqualTo(DEFAULT_GRADUADO);
        assertThat(testOtroEstudioFormal.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testOtroEstudioFormal.getTituloObtenido()).isEqualTo(DEFAULT_TITULO_OBTENIDO);
        assertThat(testOtroEstudioFormal.getNivel()).isEqualTo(DEFAULT_NIVEL);
    }

    @Test
    void fullUpdateOtroEstudioFormalWithPatch() throws Exception {
        // Initialize the database
        otroEstudioFormalRepository.save(otroEstudioFormal).block();

        int databaseSizeBeforeUpdate = otroEstudioFormalRepository.findAll().collectList().block().size();

        // Update the otroEstudioFormal using partial update
        OtroEstudioFormal partialUpdatedOtroEstudioFormal = new OtroEstudioFormal();
        partialUpdatedOtroEstudioFormal.setId(otroEstudioFormal.getId());

        partialUpdatedOtroEstudioFormal
            .institucion(UPDATED_INSTITUCION)
            .estudio(UPDATED_ESTUDIO)
            .graduado(UPDATED_GRADUADO)
            .year(UPDATED_YEAR)
            .tituloObtenido(UPDATED_TITULO_OBTENIDO)
            .nivel(UPDATED_NIVEL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedOtroEstudioFormal.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedOtroEstudioFormal))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeUpdate);
        OtroEstudioFormal testOtroEstudioFormal = otroEstudioFormalList.get(otroEstudioFormalList.size() - 1);
        assertThat(testOtroEstudioFormal.getInstitucion()).isEqualTo(UPDATED_INSTITUCION);
        assertThat(testOtroEstudioFormal.getEstudio()).isEqualTo(UPDATED_ESTUDIO);
        assertThat(testOtroEstudioFormal.getGraduado()).isEqualTo(UPDATED_GRADUADO);
        assertThat(testOtroEstudioFormal.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testOtroEstudioFormal.getTituloObtenido()).isEqualTo(UPDATED_TITULO_OBTENIDO);
        assertThat(testOtroEstudioFormal.getNivel()).isEqualTo(UPDATED_NIVEL);
    }

    @Test
    void patchNonExistingOtroEstudioFormal() throws Exception {
        int databaseSizeBeforeUpdate = otroEstudioFormalRepository.findAll().collectList().block().size();
        otroEstudioFormal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, otroEstudioFormal.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(otroEstudioFormal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOtroEstudioFormal() throws Exception {
        int databaseSizeBeforeUpdate = otroEstudioFormalRepository.findAll().collectList().block().size();
        otroEstudioFormal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(otroEstudioFormal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOtroEstudioFormal() throws Exception {
        int databaseSizeBeforeUpdate = otroEstudioFormalRepository.findAll().collectList().block().size();
        otroEstudioFormal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(otroEstudioFormal))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the OtroEstudioFormal in the database
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOtroEstudioFormal() {
        // Initialize the database
        otroEstudioFormalRepository.save(otroEstudioFormal).block();

        int databaseSizeBeforeDelete = otroEstudioFormalRepository.findAll().collectList().block().size();

        // Delete the otroEstudioFormal
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, otroEstudioFormal.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<OtroEstudioFormal> otroEstudioFormalList = otroEstudioFormalRepository.findAll().collectList().block();
        assertThat(otroEstudioFormalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
