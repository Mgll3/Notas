package ingresos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import ingresos.IntegrationTest;
import ingresos.domain.InfoBachillerato;
import ingresos.repository.EntityManager;
import ingresos.repository.InfoBachilleratoRepository;
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
 * Integration tests for the {@link InfoBachilleratoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class InfoBachilleratoResourceIT {

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_COLEGIO = "AAAAAAAAAA";
    private static final String UPDATED_COLEGIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_MODALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_MODALIDAD = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUNTAJE_ICFES = 1;
    private static final Integer UPDATED_PUNTAJE_ICFES = 2;

    private static final String ENTITY_API_URL = "/api/info-bachilleratoes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InfoBachilleratoRepository infoBachilleratoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private InfoBachillerato infoBachillerato;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoBachillerato createEntity(EntityManager em) {
        InfoBachillerato infoBachillerato = new InfoBachillerato()
            .ciudad(DEFAULT_CIUDAD)
            .departamento(DEFAULT_DEPARTAMENTO)
            .pais(DEFAULT_PAIS)
            .colegio(DEFAULT_COLEGIO)
            .year(DEFAULT_YEAR)
            .modalidad(DEFAULT_MODALIDAD)
            .puntajeIcfes(DEFAULT_PUNTAJE_ICFES);
        return infoBachillerato;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoBachillerato createUpdatedEntity(EntityManager em) {
        InfoBachillerato infoBachillerato = new InfoBachillerato()
            .ciudad(UPDATED_CIUDAD)
            .departamento(UPDATED_DEPARTAMENTO)
            .pais(UPDATED_PAIS)
            .colegio(UPDATED_COLEGIO)
            .year(UPDATED_YEAR)
            .modalidad(UPDATED_MODALIDAD)
            .puntajeIcfes(UPDATED_PUNTAJE_ICFES);
        return infoBachillerato;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(InfoBachillerato.class).block();
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
        infoBachillerato = createEntity(em);
    }

    @Test
    void createInfoBachillerato() throws Exception {
        int databaseSizeBeforeCreate = infoBachilleratoRepository.findAll().collectList().block().size();
        // Create the InfoBachillerato
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoBachillerato))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeCreate + 1);
        InfoBachillerato testInfoBachillerato = infoBachilleratoList.get(infoBachilleratoList.size() - 1);
        assertThat(testInfoBachillerato.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testInfoBachillerato.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
        assertThat(testInfoBachillerato.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testInfoBachillerato.getColegio()).isEqualTo(DEFAULT_COLEGIO);
        assertThat(testInfoBachillerato.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testInfoBachillerato.getModalidad()).isEqualTo(DEFAULT_MODALIDAD);
        assertThat(testInfoBachillerato.getPuntajeIcfes()).isEqualTo(DEFAULT_PUNTAJE_ICFES);
    }

    @Test
    void createInfoBachilleratoWithExistingId() throws Exception {
        // Create the InfoBachillerato with an existing ID
        infoBachillerato.setId(1L);

        int databaseSizeBeforeCreate = infoBachilleratoRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoBachillerato))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllInfoBachilleratoesAsStream() {
        // Initialize the database
        infoBachilleratoRepository.save(infoBachillerato).block();

        List<InfoBachillerato> infoBachilleratoList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(InfoBachillerato.class)
            .getResponseBody()
            .filter(infoBachillerato::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(infoBachilleratoList).isNotNull();
        assertThat(infoBachilleratoList).hasSize(1);
        InfoBachillerato testInfoBachillerato = infoBachilleratoList.get(0);
        assertThat(testInfoBachillerato.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testInfoBachillerato.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
        assertThat(testInfoBachillerato.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testInfoBachillerato.getColegio()).isEqualTo(DEFAULT_COLEGIO);
        assertThat(testInfoBachillerato.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testInfoBachillerato.getModalidad()).isEqualTo(DEFAULT_MODALIDAD);
        assertThat(testInfoBachillerato.getPuntajeIcfes()).isEqualTo(DEFAULT_PUNTAJE_ICFES);
    }

    @Test
    void getAllInfoBachilleratoes() {
        // Initialize the database
        infoBachilleratoRepository.save(infoBachillerato).block();

        // Get all the infoBachilleratoList
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
            .value(hasItem(infoBachillerato.getId().intValue()))
            .jsonPath("$.[*].ciudad")
            .value(hasItem(DEFAULT_CIUDAD))
            .jsonPath("$.[*].departamento")
            .value(hasItem(DEFAULT_DEPARTAMENTO))
            .jsonPath("$.[*].pais")
            .value(hasItem(DEFAULT_PAIS))
            .jsonPath("$.[*].colegio")
            .value(hasItem(DEFAULT_COLEGIO))
            .jsonPath("$.[*].year")
            .value(hasItem(DEFAULT_YEAR))
            .jsonPath("$.[*].modalidad")
            .value(hasItem(DEFAULT_MODALIDAD))
            .jsonPath("$.[*].puntajeIcfes")
            .value(hasItem(DEFAULT_PUNTAJE_ICFES));
    }

    @Test
    void getInfoBachillerato() {
        // Initialize the database
        infoBachilleratoRepository.save(infoBachillerato).block();

        // Get the infoBachillerato
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, infoBachillerato.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(infoBachillerato.getId().intValue()))
            .jsonPath("$.ciudad")
            .value(is(DEFAULT_CIUDAD))
            .jsonPath("$.departamento")
            .value(is(DEFAULT_DEPARTAMENTO))
            .jsonPath("$.pais")
            .value(is(DEFAULT_PAIS))
            .jsonPath("$.colegio")
            .value(is(DEFAULT_COLEGIO))
            .jsonPath("$.year")
            .value(is(DEFAULT_YEAR))
            .jsonPath("$.modalidad")
            .value(is(DEFAULT_MODALIDAD))
            .jsonPath("$.puntajeIcfes")
            .value(is(DEFAULT_PUNTAJE_ICFES));
    }

    @Test
    void getNonExistingInfoBachillerato() {
        // Get the infoBachillerato
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingInfoBachillerato() throws Exception {
        // Initialize the database
        infoBachilleratoRepository.save(infoBachillerato).block();

        int databaseSizeBeforeUpdate = infoBachilleratoRepository.findAll().collectList().block().size();

        // Update the infoBachillerato
        InfoBachillerato updatedInfoBachillerato = infoBachilleratoRepository.findById(infoBachillerato.getId()).block();
        updatedInfoBachillerato
            .ciudad(UPDATED_CIUDAD)
            .departamento(UPDATED_DEPARTAMENTO)
            .pais(UPDATED_PAIS)
            .colegio(UPDATED_COLEGIO)
            .year(UPDATED_YEAR)
            .modalidad(UPDATED_MODALIDAD)
            .puntajeIcfes(UPDATED_PUNTAJE_ICFES);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedInfoBachillerato.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedInfoBachillerato))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeUpdate);
        InfoBachillerato testInfoBachillerato = infoBachilleratoList.get(infoBachilleratoList.size() - 1);
        assertThat(testInfoBachillerato.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testInfoBachillerato.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
        assertThat(testInfoBachillerato.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testInfoBachillerato.getColegio()).isEqualTo(UPDATED_COLEGIO);
        assertThat(testInfoBachillerato.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testInfoBachillerato.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
        assertThat(testInfoBachillerato.getPuntajeIcfes()).isEqualTo(UPDATED_PUNTAJE_ICFES);
    }

    @Test
    void putNonExistingInfoBachillerato() throws Exception {
        int databaseSizeBeforeUpdate = infoBachilleratoRepository.findAll().collectList().block().size();
        infoBachillerato.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, infoBachillerato.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoBachillerato))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchInfoBachillerato() throws Exception {
        int databaseSizeBeforeUpdate = infoBachilleratoRepository.findAll().collectList().block().size();
        infoBachillerato.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoBachillerato))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamInfoBachillerato() throws Exception {
        int databaseSizeBeforeUpdate = infoBachilleratoRepository.findAll().collectList().block().size();
        infoBachillerato.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoBachillerato))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateInfoBachilleratoWithPatch() throws Exception {
        // Initialize the database
        infoBachilleratoRepository.save(infoBachillerato).block();

        int databaseSizeBeforeUpdate = infoBachilleratoRepository.findAll().collectList().block().size();

        // Update the infoBachillerato using partial update
        InfoBachillerato partialUpdatedInfoBachillerato = new InfoBachillerato();
        partialUpdatedInfoBachillerato.setId(infoBachillerato.getId());

        partialUpdatedInfoBachillerato.ciudad(UPDATED_CIUDAD).pais(UPDATED_PAIS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedInfoBachillerato.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoBachillerato))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeUpdate);
        InfoBachillerato testInfoBachillerato = infoBachilleratoList.get(infoBachilleratoList.size() - 1);
        assertThat(testInfoBachillerato.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testInfoBachillerato.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
        assertThat(testInfoBachillerato.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testInfoBachillerato.getColegio()).isEqualTo(DEFAULT_COLEGIO);
        assertThat(testInfoBachillerato.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testInfoBachillerato.getModalidad()).isEqualTo(DEFAULT_MODALIDAD);
        assertThat(testInfoBachillerato.getPuntajeIcfes()).isEqualTo(DEFAULT_PUNTAJE_ICFES);
    }

    @Test
    void fullUpdateInfoBachilleratoWithPatch() throws Exception {
        // Initialize the database
        infoBachilleratoRepository.save(infoBachillerato).block();

        int databaseSizeBeforeUpdate = infoBachilleratoRepository.findAll().collectList().block().size();

        // Update the infoBachillerato using partial update
        InfoBachillerato partialUpdatedInfoBachillerato = new InfoBachillerato();
        partialUpdatedInfoBachillerato.setId(infoBachillerato.getId());

        partialUpdatedInfoBachillerato
            .ciudad(UPDATED_CIUDAD)
            .departamento(UPDATED_DEPARTAMENTO)
            .pais(UPDATED_PAIS)
            .colegio(UPDATED_COLEGIO)
            .year(UPDATED_YEAR)
            .modalidad(UPDATED_MODALIDAD)
            .puntajeIcfes(UPDATED_PUNTAJE_ICFES);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedInfoBachillerato.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoBachillerato))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeUpdate);
        InfoBachillerato testInfoBachillerato = infoBachilleratoList.get(infoBachilleratoList.size() - 1);
        assertThat(testInfoBachillerato.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testInfoBachillerato.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
        assertThat(testInfoBachillerato.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testInfoBachillerato.getColegio()).isEqualTo(UPDATED_COLEGIO);
        assertThat(testInfoBachillerato.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testInfoBachillerato.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
        assertThat(testInfoBachillerato.getPuntajeIcfes()).isEqualTo(UPDATED_PUNTAJE_ICFES);
    }

    @Test
    void patchNonExistingInfoBachillerato() throws Exception {
        int databaseSizeBeforeUpdate = infoBachilleratoRepository.findAll().collectList().block().size();
        infoBachillerato.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, infoBachillerato.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoBachillerato))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchInfoBachillerato() throws Exception {
        int databaseSizeBeforeUpdate = infoBachilleratoRepository.findAll().collectList().block().size();
        infoBachillerato.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoBachillerato))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamInfoBachillerato() throws Exception {
        int databaseSizeBeforeUpdate = infoBachilleratoRepository.findAll().collectList().block().size();
        infoBachillerato.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoBachillerato))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the InfoBachillerato in the database
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteInfoBachillerato() {
        // Initialize the database
        infoBachilleratoRepository.save(infoBachillerato).block();

        int databaseSizeBeforeDelete = infoBachilleratoRepository.findAll().collectList().block().size();

        // Delete the infoBachillerato
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, infoBachillerato.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<InfoBachillerato> infoBachilleratoList = infoBachilleratoRepository.findAll().collectList().block();
        assertThat(infoBachilleratoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
