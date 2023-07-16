package ingresos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import ingresos.IntegrationTest;
import ingresos.domain.Programa;
import ingresos.repository.EntityManager;
import ingresos.repository.ProgramaRepository;
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
 * Integration tests for the {@link ProgramaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProgramaResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_RESTRICCION = "AAAAAAAAAA";
    private static final String UPDATED_RESTRICCION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/programas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Programa programa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programa createEntity(EntityManager em) {
        Programa programa = new Programa()
            .codigo(DEFAULT_CODIGO)
            .nombre(DEFAULT_NOMBRE)
            .restriccion(DEFAULT_RESTRICCION)
            .descripcion(DEFAULT_DESCRIPCION);
        return programa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programa createUpdatedEntity(EntityManager em) {
        Programa programa = new Programa()
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .restriccion(UPDATED_RESTRICCION)
            .descripcion(UPDATED_DESCRIPCION);
        return programa;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Programa.class).block();
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
        programa = createEntity(em);
    }

    @Test
    void createPrograma() throws Exception {
        int databaseSizeBeforeCreate = programaRepository.findAll().collectList().block().size();
        // Create the Programa
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(programa))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeCreate + 1);
        Programa testPrograma = programaList.get(programaList.size() - 1);
        assertThat(testPrograma.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testPrograma.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPrograma.getRestriccion()).isEqualTo(DEFAULT_RESTRICCION);
        assertThat(testPrograma.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    void createProgramaWithExistingId() throws Exception {
        // Create the Programa with an existing ID
        programa.setId(1L);

        int databaseSizeBeforeCreate = programaRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(programa))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProgramasAsStream() {
        // Initialize the database
        programaRepository.save(programa).block();

        List<Programa> programaList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Programa.class)
            .getResponseBody()
            .filter(programa::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(programaList).isNotNull();
        assertThat(programaList).hasSize(1);
        Programa testPrograma = programaList.get(0);
        assertThat(testPrograma.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testPrograma.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPrograma.getRestriccion()).isEqualTo(DEFAULT_RESTRICCION);
        assertThat(testPrograma.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    void getAllProgramas() {
        // Initialize the database
        programaRepository.save(programa).block();

        // Get all the programaList
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
            .value(hasItem(programa.getId().intValue()))
            .jsonPath("$.[*].codigo")
            .value(hasItem(DEFAULT_CODIGO))
            .jsonPath("$.[*].nombre")
            .value(hasItem(DEFAULT_NOMBRE))
            .jsonPath("$.[*].restriccion")
            .value(hasItem(DEFAULT_RESTRICCION))
            .jsonPath("$.[*].descripcion")
            .value(hasItem(DEFAULT_DESCRIPCION));
    }

    @Test
    void getPrograma() {
        // Initialize the database
        programaRepository.save(programa).block();

        // Get the programa
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, programa.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(programa.getId().intValue()))
            .jsonPath("$.codigo")
            .value(is(DEFAULT_CODIGO))
            .jsonPath("$.nombre")
            .value(is(DEFAULT_NOMBRE))
            .jsonPath("$.restriccion")
            .value(is(DEFAULT_RESTRICCION))
            .jsonPath("$.descripcion")
            .value(is(DEFAULT_DESCRIPCION));
    }

    @Test
    void getNonExistingPrograma() {
        // Get the programa
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPrograma() throws Exception {
        // Initialize the database
        programaRepository.save(programa).block();

        int databaseSizeBeforeUpdate = programaRepository.findAll().collectList().block().size();

        // Update the programa
        Programa updatedPrograma = programaRepository.findById(programa.getId()).block();
        updatedPrograma.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).restriccion(UPDATED_RESTRICCION).descripcion(UPDATED_DESCRIPCION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedPrograma.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedPrograma))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
        Programa testPrograma = programaList.get(programaList.size() - 1);
        assertThat(testPrograma.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPrograma.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPrograma.getRestriccion()).isEqualTo(UPDATED_RESTRICCION);
        assertThat(testPrograma.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    void putNonExistingPrograma() throws Exception {
        int databaseSizeBeforeUpdate = programaRepository.findAll().collectList().block().size();
        programa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, programa.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(programa))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPrograma() throws Exception {
        int databaseSizeBeforeUpdate = programaRepository.findAll().collectList().block().size();
        programa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(programa))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPrograma() throws Exception {
        int databaseSizeBeforeUpdate = programaRepository.findAll().collectList().block().size();
        programa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(programa))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProgramaWithPatch() throws Exception {
        // Initialize the database
        programaRepository.save(programa).block();

        int databaseSizeBeforeUpdate = programaRepository.findAll().collectList().block().size();

        // Update the programa using partial update
        Programa partialUpdatedPrograma = new Programa();
        partialUpdatedPrograma.setId(programa.getId());

        partialUpdatedPrograma.descripcion(UPDATED_DESCRIPCION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPrograma.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPrograma))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
        Programa testPrograma = programaList.get(programaList.size() - 1);
        assertThat(testPrograma.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testPrograma.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPrograma.getRestriccion()).isEqualTo(DEFAULT_RESTRICCION);
        assertThat(testPrograma.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    void fullUpdateProgramaWithPatch() throws Exception {
        // Initialize the database
        programaRepository.save(programa).block();

        int databaseSizeBeforeUpdate = programaRepository.findAll().collectList().block().size();

        // Update the programa using partial update
        Programa partialUpdatedPrograma = new Programa();
        partialUpdatedPrograma.setId(programa.getId());

        partialUpdatedPrograma
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .restriccion(UPDATED_RESTRICCION)
            .descripcion(UPDATED_DESCRIPCION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPrograma.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPrograma))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
        Programa testPrograma = programaList.get(programaList.size() - 1);
        assertThat(testPrograma.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPrograma.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPrograma.getRestriccion()).isEqualTo(UPDATED_RESTRICCION);
        assertThat(testPrograma.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    void patchNonExistingPrograma() throws Exception {
        int databaseSizeBeforeUpdate = programaRepository.findAll().collectList().block().size();
        programa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, programa.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(programa))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPrograma() throws Exception {
        int databaseSizeBeforeUpdate = programaRepository.findAll().collectList().block().size();
        programa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(programa))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPrograma() throws Exception {
        int databaseSizeBeforeUpdate = programaRepository.findAll().collectList().block().size();
        programa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(programa))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePrograma() {
        // Initialize the database
        programaRepository.save(programa).block();

        int databaseSizeBeforeDelete = programaRepository.findAll().collectList().block().size();

        // Delete the programa
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, programa.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Programa> programaList = programaRepository.findAll().collectList().block();
        assertThat(programaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
