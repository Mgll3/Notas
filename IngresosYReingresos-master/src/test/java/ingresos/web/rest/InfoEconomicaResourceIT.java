package ingresos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import ingresos.IntegrationTest;
import ingresos.domain.InfoEconomica;
import ingresos.repository.EntityManager;
import ingresos.repository.InfoEconomicaRepository;
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
 * Integration tests for the {@link InfoEconomicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class InfoEconomicaResourceIT {

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_BARRIO = "AAAAAAAAAA";
    private static final String UPDATED_BARRIO = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTRATO = 1;
    private static final Integer UPDATED_ESTRATO = 2;

    private static final Boolean DEFAULT_DEPENDE_ECONO = false;
    private static final Boolean UPDATED_DEPENDE_ECONO = true;

    private static final Boolean DEFAULT_TRABAJA = false;
    private static final Boolean UPDATED_TRABAJA = true;

    private static final Boolean DEFAULT_TIENE_DEPENDIENTES = false;
    private static final Boolean UPDATED_TIENE_DEPENDIENTES = true;

    private static final String ENTITY_API_URL = "/api/info-economicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InfoEconomicaRepository infoEconomicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private InfoEconomica infoEconomica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoEconomica createEntity(EntityManager em) {
        InfoEconomica infoEconomica = new InfoEconomica()
            .direccion(DEFAULT_DIRECCION)
            .barrio(DEFAULT_BARRIO)
            .ciudad(DEFAULT_CIUDAD)
            .departmento(DEFAULT_DEPARTMENTO)
            .estrato(DEFAULT_ESTRATO)
            .dependeEcono(DEFAULT_DEPENDE_ECONO)
            .trabaja(DEFAULT_TRABAJA)
            .tieneDependientes(DEFAULT_TIENE_DEPENDIENTES);
        return infoEconomica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoEconomica createUpdatedEntity(EntityManager em) {
        InfoEconomica infoEconomica = new InfoEconomica()
            .direccion(UPDATED_DIRECCION)
            .barrio(UPDATED_BARRIO)
            .ciudad(UPDATED_CIUDAD)
            .departmento(UPDATED_DEPARTMENTO)
            .estrato(UPDATED_ESTRATO)
            .dependeEcono(UPDATED_DEPENDE_ECONO)
            .trabaja(UPDATED_TRABAJA)
            .tieneDependientes(UPDATED_TIENE_DEPENDIENTES);
        return infoEconomica;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(InfoEconomica.class).block();
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
        infoEconomica = createEntity(em);
    }

    @Test
    void createInfoEconomica() throws Exception {
        int databaseSizeBeforeCreate = infoEconomicaRepository.findAll().collectList().block().size();
        // Create the InfoEconomica
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoEconomica))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeCreate + 1);
        InfoEconomica testInfoEconomica = infoEconomicaList.get(infoEconomicaList.size() - 1);
        assertThat(testInfoEconomica.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testInfoEconomica.getBarrio()).isEqualTo(DEFAULT_BARRIO);
        assertThat(testInfoEconomica.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testInfoEconomica.getDepartmento()).isEqualTo(DEFAULT_DEPARTMENTO);
        assertThat(testInfoEconomica.getEstrato()).isEqualTo(DEFAULT_ESTRATO);
        assertThat(testInfoEconomica.getDependeEcono()).isEqualTo(DEFAULT_DEPENDE_ECONO);
        assertThat(testInfoEconomica.getTrabaja()).isEqualTo(DEFAULT_TRABAJA);
        assertThat(testInfoEconomica.getTieneDependientes()).isEqualTo(DEFAULT_TIENE_DEPENDIENTES);
    }

    @Test
    void createInfoEconomicaWithExistingId() throws Exception {
        // Create the InfoEconomica with an existing ID
        infoEconomica.setId(1L);

        int databaseSizeBeforeCreate = infoEconomicaRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoEconomica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllInfoEconomicasAsStream() {
        // Initialize the database
        infoEconomicaRepository.save(infoEconomica).block();

        List<InfoEconomica> infoEconomicaList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(InfoEconomica.class)
            .getResponseBody()
            .filter(infoEconomica::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(infoEconomicaList).isNotNull();
        assertThat(infoEconomicaList).hasSize(1);
        InfoEconomica testInfoEconomica = infoEconomicaList.get(0);
        assertThat(testInfoEconomica.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testInfoEconomica.getBarrio()).isEqualTo(DEFAULT_BARRIO);
        assertThat(testInfoEconomica.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testInfoEconomica.getDepartmento()).isEqualTo(DEFAULT_DEPARTMENTO);
        assertThat(testInfoEconomica.getEstrato()).isEqualTo(DEFAULT_ESTRATO);
        assertThat(testInfoEconomica.getDependeEcono()).isEqualTo(DEFAULT_DEPENDE_ECONO);
        assertThat(testInfoEconomica.getTrabaja()).isEqualTo(DEFAULT_TRABAJA);
        assertThat(testInfoEconomica.getTieneDependientes()).isEqualTo(DEFAULT_TIENE_DEPENDIENTES);
    }

    @Test
    void getAllInfoEconomicas() {
        // Initialize the database
        infoEconomicaRepository.save(infoEconomica).block();

        // Get all the infoEconomicaList
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
            .value(hasItem(infoEconomica.getId().intValue()))
            .jsonPath("$.[*].direccion")
            .value(hasItem(DEFAULT_DIRECCION))
            .jsonPath("$.[*].barrio")
            .value(hasItem(DEFAULT_BARRIO))
            .jsonPath("$.[*].ciudad")
            .value(hasItem(DEFAULT_CIUDAD))
            .jsonPath("$.[*].departmento")
            .value(hasItem(DEFAULT_DEPARTMENTO))
            .jsonPath("$.[*].estrato")
            .value(hasItem(DEFAULT_ESTRATO))
            .jsonPath("$.[*].dependeEcono")
            .value(hasItem(DEFAULT_DEPENDE_ECONO.booleanValue()))
            .jsonPath("$.[*].trabaja")
            .value(hasItem(DEFAULT_TRABAJA.booleanValue()))
            .jsonPath("$.[*].tieneDependientes")
            .value(hasItem(DEFAULT_TIENE_DEPENDIENTES.booleanValue()));
    }

    @Test
    void getInfoEconomica() {
        // Initialize the database
        infoEconomicaRepository.save(infoEconomica).block();

        // Get the infoEconomica
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, infoEconomica.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(infoEconomica.getId().intValue()))
            .jsonPath("$.direccion")
            .value(is(DEFAULT_DIRECCION))
            .jsonPath("$.barrio")
            .value(is(DEFAULT_BARRIO))
            .jsonPath("$.ciudad")
            .value(is(DEFAULT_CIUDAD))
            .jsonPath("$.departmento")
            .value(is(DEFAULT_DEPARTMENTO))
            .jsonPath("$.estrato")
            .value(is(DEFAULT_ESTRATO))
            .jsonPath("$.dependeEcono")
            .value(is(DEFAULT_DEPENDE_ECONO.booleanValue()))
            .jsonPath("$.trabaja")
            .value(is(DEFAULT_TRABAJA.booleanValue()))
            .jsonPath("$.tieneDependientes")
            .value(is(DEFAULT_TIENE_DEPENDIENTES.booleanValue()));
    }

    @Test
    void getNonExistingInfoEconomica() {
        // Get the infoEconomica
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingInfoEconomica() throws Exception {
        // Initialize the database
        infoEconomicaRepository.save(infoEconomica).block();

        int databaseSizeBeforeUpdate = infoEconomicaRepository.findAll().collectList().block().size();

        // Update the infoEconomica
        InfoEconomica updatedInfoEconomica = infoEconomicaRepository.findById(infoEconomica.getId()).block();
        updatedInfoEconomica
            .direccion(UPDATED_DIRECCION)
            .barrio(UPDATED_BARRIO)
            .ciudad(UPDATED_CIUDAD)
            .departmento(UPDATED_DEPARTMENTO)
            .estrato(UPDATED_ESTRATO)
            .dependeEcono(UPDATED_DEPENDE_ECONO)
            .trabaja(UPDATED_TRABAJA)
            .tieneDependientes(UPDATED_TIENE_DEPENDIENTES);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedInfoEconomica.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedInfoEconomica))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeUpdate);
        InfoEconomica testInfoEconomica = infoEconomicaList.get(infoEconomicaList.size() - 1);
        assertThat(testInfoEconomica.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testInfoEconomica.getBarrio()).isEqualTo(UPDATED_BARRIO);
        assertThat(testInfoEconomica.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testInfoEconomica.getDepartmento()).isEqualTo(UPDATED_DEPARTMENTO);
        assertThat(testInfoEconomica.getEstrato()).isEqualTo(UPDATED_ESTRATO);
        assertThat(testInfoEconomica.getDependeEcono()).isEqualTo(UPDATED_DEPENDE_ECONO);
        assertThat(testInfoEconomica.getTrabaja()).isEqualTo(UPDATED_TRABAJA);
        assertThat(testInfoEconomica.getTieneDependientes()).isEqualTo(UPDATED_TIENE_DEPENDIENTES);
    }

    @Test
    void putNonExistingInfoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = infoEconomicaRepository.findAll().collectList().block().size();
        infoEconomica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, infoEconomica.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoEconomica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchInfoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = infoEconomicaRepository.findAll().collectList().block().size();
        infoEconomica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoEconomica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamInfoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = infoEconomicaRepository.findAll().collectList().block().size();
        infoEconomica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoEconomica))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateInfoEconomicaWithPatch() throws Exception {
        // Initialize the database
        infoEconomicaRepository.save(infoEconomica).block();

        int databaseSizeBeforeUpdate = infoEconomicaRepository.findAll().collectList().block().size();

        // Update the infoEconomica using partial update
        InfoEconomica partialUpdatedInfoEconomica = new InfoEconomica();
        partialUpdatedInfoEconomica.setId(infoEconomica.getId());

        partialUpdatedInfoEconomica
            .direccion(UPDATED_DIRECCION)
            .ciudad(UPDATED_CIUDAD)
            .departmento(UPDATED_DEPARTMENTO)
            .trabaja(UPDATED_TRABAJA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedInfoEconomica.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoEconomica))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeUpdate);
        InfoEconomica testInfoEconomica = infoEconomicaList.get(infoEconomicaList.size() - 1);
        assertThat(testInfoEconomica.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testInfoEconomica.getBarrio()).isEqualTo(DEFAULT_BARRIO);
        assertThat(testInfoEconomica.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testInfoEconomica.getDepartmento()).isEqualTo(UPDATED_DEPARTMENTO);
        assertThat(testInfoEconomica.getEstrato()).isEqualTo(DEFAULT_ESTRATO);
        assertThat(testInfoEconomica.getDependeEcono()).isEqualTo(DEFAULT_DEPENDE_ECONO);
        assertThat(testInfoEconomica.getTrabaja()).isEqualTo(UPDATED_TRABAJA);
        assertThat(testInfoEconomica.getTieneDependientes()).isEqualTo(DEFAULT_TIENE_DEPENDIENTES);
    }

    @Test
    void fullUpdateInfoEconomicaWithPatch() throws Exception {
        // Initialize the database
        infoEconomicaRepository.save(infoEconomica).block();

        int databaseSizeBeforeUpdate = infoEconomicaRepository.findAll().collectList().block().size();

        // Update the infoEconomica using partial update
        InfoEconomica partialUpdatedInfoEconomica = new InfoEconomica();
        partialUpdatedInfoEconomica.setId(infoEconomica.getId());

        partialUpdatedInfoEconomica
            .direccion(UPDATED_DIRECCION)
            .barrio(UPDATED_BARRIO)
            .ciudad(UPDATED_CIUDAD)
            .departmento(UPDATED_DEPARTMENTO)
            .estrato(UPDATED_ESTRATO)
            .dependeEcono(UPDATED_DEPENDE_ECONO)
            .trabaja(UPDATED_TRABAJA)
            .tieneDependientes(UPDATED_TIENE_DEPENDIENTES);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedInfoEconomica.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoEconomica))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeUpdate);
        InfoEconomica testInfoEconomica = infoEconomicaList.get(infoEconomicaList.size() - 1);
        assertThat(testInfoEconomica.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testInfoEconomica.getBarrio()).isEqualTo(UPDATED_BARRIO);
        assertThat(testInfoEconomica.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testInfoEconomica.getDepartmento()).isEqualTo(UPDATED_DEPARTMENTO);
        assertThat(testInfoEconomica.getEstrato()).isEqualTo(UPDATED_ESTRATO);
        assertThat(testInfoEconomica.getDependeEcono()).isEqualTo(UPDATED_DEPENDE_ECONO);
        assertThat(testInfoEconomica.getTrabaja()).isEqualTo(UPDATED_TRABAJA);
        assertThat(testInfoEconomica.getTieneDependientes()).isEqualTo(UPDATED_TIENE_DEPENDIENTES);
    }

    @Test
    void patchNonExistingInfoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = infoEconomicaRepository.findAll().collectList().block().size();
        infoEconomica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, infoEconomica.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoEconomica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchInfoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = infoEconomicaRepository.findAll().collectList().block().size();
        infoEconomica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoEconomica))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamInfoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = infoEconomicaRepository.findAll().collectList().block().size();
        infoEconomica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoEconomica))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the InfoEconomica in the database
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteInfoEconomica() {
        // Initialize the database
        infoEconomicaRepository.save(infoEconomica).block();

        int databaseSizeBeforeDelete = infoEconomicaRepository.findAll().collectList().block().size();

        // Delete the infoEconomica
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, infoEconomica.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<InfoEconomica> infoEconomicaList = infoEconomicaRepository.findAll().collectList().block();
        assertThat(infoEconomicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
