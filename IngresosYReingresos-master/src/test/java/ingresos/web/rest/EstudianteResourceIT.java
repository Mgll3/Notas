package ingresos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import ingresos.IntegrationTest;
import ingresos.domain.Estudiante;
import ingresos.domain.InfoAcademica;
import ingresos.domain.InfoEconomica;
import ingresos.domain.InfoPersonal;
import ingresos.repository.EntityManager;
import ingresos.repository.EstudianteRepository;
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
 * Integration tests for the {@link EstudianteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class EstudianteResourceIT {

    private static final Boolean DEFAULT_DILIGENCIADO = false;
    private static final Boolean UPDATED_DILIGENCIADO = true;

    private static final String ENTITY_API_URL = "/api/estudiantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Estudiante estudiante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estudiante createEntity(EntityManager em) {
        Estudiante estudiante = new Estudiante().diligenciado(DEFAULT_DILIGENCIADO);
        // Add required entity
        InfoPersonal infoPersonal;
        infoPersonal = em.insert(InfoPersonalResourceIT.createEntity(em)).block();
        estudiante.setEstudianteP(infoPersonal);
        // Add required entity
        InfoEconomica infoEconomica;
        infoEconomica = em.insert(InfoEconomicaResourceIT.createEntity(em)).block();
        estudiante.setEstudianteE(infoEconomica);
        // Add required entity
        InfoAcademica infoAcademica;
        infoAcademica = em.insert(InfoAcademicaResourceIT.createEntity(em)).block();
        estudiante.setEstudianteA(infoAcademica);
        return estudiante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estudiante createUpdatedEntity(EntityManager em) {
        Estudiante estudiante = new Estudiante().diligenciado(UPDATED_DILIGENCIADO);
        // Add required entity
        InfoPersonal infoPersonal;
        infoPersonal = em.insert(InfoPersonalResourceIT.createUpdatedEntity(em)).block();
        estudiante.setEstudianteP(infoPersonal);
        // Add required entity
        InfoEconomica infoEconomica;
        infoEconomica = em.insert(InfoEconomicaResourceIT.createUpdatedEntity(em)).block();
        estudiante.setEstudianteE(infoEconomica);
        // Add required entity
        InfoAcademica infoAcademica;
        infoAcademica = em.insert(InfoAcademicaResourceIT.createUpdatedEntity(em)).block();
        estudiante.setEstudianteA(infoAcademica);
        return estudiante;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Estudiante.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        InfoPersonalResourceIT.deleteEntities(em);
        InfoEconomicaResourceIT.deleteEntities(em);
        InfoAcademicaResourceIT.deleteEntities(em);
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
        estudiante = createEntity(em);
    }

    @Test
    void createEstudiante() throws Exception {
        int databaseSizeBeforeCreate = estudianteRepository.findAll().collectList().block().size();
        // Create the Estudiante
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estudiante))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate + 1);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getDiligenciado()).isEqualTo(DEFAULT_DILIGENCIADO);
    }

    @Test
    void createEstudianteWithExistingId() throws Exception {
        // Create the Estudiante with an existing ID
        estudiante.setId(1L);

        int databaseSizeBeforeCreate = estudianteRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estudiante))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEstudiantesAsStream() {
        // Initialize the database
        estudianteRepository.save(estudiante).block();

        List<Estudiante> estudianteList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Estudiante.class)
            .getResponseBody()
            .filter(estudiante::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(estudianteList).isNotNull();
        assertThat(estudianteList).hasSize(1);
        Estudiante testEstudiante = estudianteList.get(0);
        assertThat(testEstudiante.getDiligenciado()).isEqualTo(DEFAULT_DILIGENCIADO);
    }

    @Test
    void getAllEstudiantes() {
        // Initialize the database
        estudianteRepository.save(estudiante).block();

        // Get all the estudianteList
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
            .value(hasItem(estudiante.getId().intValue()))
            .jsonPath("$.[*].diligenciado")
            .value(hasItem(DEFAULT_DILIGENCIADO.booleanValue()));
    }

    @Test
    void getEstudiante() {
        // Initialize the database
        estudianteRepository.save(estudiante).block();

        // Get the estudiante
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, estudiante.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(estudiante.getId().intValue()))
            .jsonPath("$.diligenciado")
            .value(is(DEFAULT_DILIGENCIADO.booleanValue()));
    }

    @Test
    void getNonExistingEstudiante() {
        // Get the estudiante
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.save(estudiante).block();

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().collectList().block().size();

        // Update the estudiante
        Estudiante updatedEstudiante = estudianteRepository.findById(estudiante.getId()).block();
        updatedEstudiante.diligenciado(UPDATED_DILIGENCIADO);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedEstudiante.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedEstudiante))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getDiligenciado()).isEqualTo(UPDATED_DILIGENCIADO);
    }

    @Test
    void putNonExistingEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().collectList().block().size();
        estudiante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, estudiante.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estudiante))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().collectList().block().size();
        estudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estudiante))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().collectList().block().size();
        estudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(estudiante))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEstudianteWithPatch() throws Exception {
        // Initialize the database
        estudianteRepository.save(estudiante).block();

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().collectList().block().size();

        // Update the estudiante using partial update
        Estudiante partialUpdatedEstudiante = new Estudiante();
        partialUpdatedEstudiante.setId(estudiante.getId());

        partialUpdatedEstudiante.diligenciado(UPDATED_DILIGENCIADO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEstudiante.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudiante))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getDiligenciado()).isEqualTo(UPDATED_DILIGENCIADO);
    }

    @Test
    void fullUpdateEstudianteWithPatch() throws Exception {
        // Initialize the database
        estudianteRepository.save(estudiante).block();

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().collectList().block().size();

        // Update the estudiante using partial update
        Estudiante partialUpdatedEstudiante = new Estudiante();
        partialUpdatedEstudiante.setId(estudiante.getId());

        partialUpdatedEstudiante.diligenciado(UPDATED_DILIGENCIADO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEstudiante.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudiante))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getDiligenciado()).isEqualTo(UPDATED_DILIGENCIADO);
    }

    @Test
    void patchNonExistingEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().collectList().block().size();
        estudiante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, estudiante.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(estudiante))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().collectList().block().size();
        estudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(estudiante))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().collectList().block().size();
        estudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(estudiante))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEstudiante() {
        // Initialize the database
        estudianteRepository.save(estudiante).block();

        int databaseSizeBeforeDelete = estudianteRepository.findAll().collectList().block().size();

        // Delete the estudiante
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, estudiante.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Estudiante> estudianteList = estudianteRepository.findAll().collectList().block();
        assertThat(estudianteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
