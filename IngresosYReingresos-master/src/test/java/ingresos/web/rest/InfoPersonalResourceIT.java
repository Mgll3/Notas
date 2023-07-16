package ingresos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import ingresos.IntegrationTest;
import ingresos.domain.InfoPersonal;
import ingresos.repository.EntityManager;
import ingresos.repository.InfoPersonalRepository;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link InfoPersonalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class InfoPersonalResourceIT {

    private static final String DEFAULT_TIPO_ID = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_NACIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_NACIMIENTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD_INGRESO = 1;
    private static final Integer UPDATED_EDAD_INGRESO = 2;

    private static final Integer DEFAULT_TELEFONO = 1;
    private static final Integer UPDATED_TELEFONO = 2;

    private static final Integer DEFAULT_CELULAR = 1;
    private static final Integer UPDATED_CELULAR = 2;

    private static final String DEFAULT_CORREO_PERSONAL = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_PERSONAL = "BBBBBBBBBB";

    private static final String DEFAULT_NACIONALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NACIONALIDAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/info-personals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private InfoPersonalRepository infoPersonalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private InfoPersonal infoPersonal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoPersonal createEntity(EntityManager em) {
        InfoPersonal infoPersonal = new InfoPersonal()
            .tipoID(DEFAULT_TIPO_ID)
            .nombre(DEFAULT_NOMBRE)
            .apellidos(DEFAULT_APELLIDOS)
            .genero(DEFAULT_GENERO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .edadIngreso(DEFAULT_EDAD_INGRESO)
            .telefono(DEFAULT_TELEFONO)
            .celular(DEFAULT_CELULAR)
            .correoPersonal(DEFAULT_CORREO_PERSONAL)
            .nacionalidad(DEFAULT_NACIONALIDAD);
        return infoPersonal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoPersonal createUpdatedEntity(EntityManager em) {
        InfoPersonal infoPersonal = new InfoPersonal()
            .tipoID(UPDATED_TIPO_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .genero(UPDATED_GENERO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .edadIngreso(UPDATED_EDAD_INGRESO)
            .telefono(UPDATED_TELEFONO)
            .celular(UPDATED_CELULAR)
            .correoPersonal(UPDATED_CORREO_PERSONAL)
            .nacionalidad(UPDATED_NACIONALIDAD);
        return infoPersonal;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(InfoPersonal.class).block();
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
        infoPersonal = createEntity(em);
    }

    @Test
    void createInfoPersonal() throws Exception {
        int databaseSizeBeforeCreate = infoPersonalRepository.findAll().collectList().block().size();
        // Create the InfoPersonal
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoPersonal))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeCreate + 1);
        InfoPersonal testInfoPersonal = infoPersonalList.get(infoPersonalList.size() - 1);
        assertThat(testInfoPersonal.getTipoID()).isEqualTo(DEFAULT_TIPO_ID);
        assertThat(testInfoPersonal.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testInfoPersonal.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testInfoPersonal.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testInfoPersonal.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testInfoPersonal.getEdadIngreso()).isEqualTo(DEFAULT_EDAD_INGRESO);
        assertThat(testInfoPersonal.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testInfoPersonal.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testInfoPersonal.getCorreoPersonal()).isEqualTo(DEFAULT_CORREO_PERSONAL);
        assertThat(testInfoPersonal.getNacionalidad()).isEqualTo(DEFAULT_NACIONALIDAD);
    }

    @Test
    void createInfoPersonalWithExistingId() throws Exception {
        // Create the InfoPersonal with an existing ID
        infoPersonal.setId("existing_id");

        int databaseSizeBeforeCreate = infoPersonalRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoPersonal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllInfoPersonalsAsStream() {
        // Initialize the database
        infoPersonal.setId(UUID.randomUUID().toString());
        infoPersonalRepository.save(infoPersonal).block();

        List<InfoPersonal> infoPersonalList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(InfoPersonal.class)
            .getResponseBody()
            .filter(infoPersonal::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(infoPersonalList).isNotNull();
        assertThat(infoPersonalList).hasSize(1);
        InfoPersonal testInfoPersonal = infoPersonalList.get(0);
        assertThat(testInfoPersonal.getTipoID()).isEqualTo(DEFAULT_TIPO_ID);
        assertThat(testInfoPersonal.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testInfoPersonal.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testInfoPersonal.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testInfoPersonal.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testInfoPersonal.getEdadIngreso()).isEqualTo(DEFAULT_EDAD_INGRESO);
        assertThat(testInfoPersonal.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testInfoPersonal.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testInfoPersonal.getCorreoPersonal()).isEqualTo(DEFAULT_CORREO_PERSONAL);
        assertThat(testInfoPersonal.getNacionalidad()).isEqualTo(DEFAULT_NACIONALIDAD);
    }

    @Test
    void getAllInfoPersonals() {
        // Initialize the database
        infoPersonal.setId(UUID.randomUUID().toString());
        infoPersonalRepository.save(infoPersonal).block();

        // Get all the infoPersonalList
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
            .value(hasItem(infoPersonal.getId()))
            .jsonPath("$.[*].tipoID")
            .value(hasItem(DEFAULT_TIPO_ID))
            .jsonPath("$.[*].nombre")
            .value(hasItem(DEFAULT_NOMBRE))
            .jsonPath("$.[*].apellidos")
            .value(hasItem(DEFAULT_APELLIDOS))
            .jsonPath("$.[*].genero")
            .value(hasItem(DEFAULT_GENERO))
            .jsonPath("$.[*].fechaNacimiento")
            .value(hasItem(DEFAULT_FECHA_NACIMIENTO))
            .jsonPath("$.[*].edadIngreso")
            .value(hasItem(DEFAULT_EDAD_INGRESO))
            .jsonPath("$.[*].telefono")
            .value(hasItem(DEFAULT_TELEFONO))
            .jsonPath("$.[*].celular")
            .value(hasItem(DEFAULT_CELULAR))
            .jsonPath("$.[*].correoPersonal")
            .value(hasItem(DEFAULT_CORREO_PERSONAL))
            .jsonPath("$.[*].nacionalidad")
            .value(hasItem(DEFAULT_NACIONALIDAD));
    }

    @Test
    void getInfoPersonal() {
        // Initialize the database
        infoPersonal.setId(UUID.randomUUID().toString());
        infoPersonalRepository.save(infoPersonal).block();

        // Get the infoPersonal
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, infoPersonal.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(infoPersonal.getId()))
            .jsonPath("$.tipoID")
            .value(is(DEFAULT_TIPO_ID))
            .jsonPath("$.nombre")
            .value(is(DEFAULT_NOMBRE))
            .jsonPath("$.apellidos")
            .value(is(DEFAULT_APELLIDOS))
            .jsonPath("$.genero")
            .value(is(DEFAULT_GENERO))
            .jsonPath("$.fechaNacimiento")
            .value(is(DEFAULT_FECHA_NACIMIENTO))
            .jsonPath("$.edadIngreso")
            .value(is(DEFAULT_EDAD_INGRESO))
            .jsonPath("$.telefono")
            .value(is(DEFAULT_TELEFONO))
            .jsonPath("$.celular")
            .value(is(DEFAULT_CELULAR))
            .jsonPath("$.correoPersonal")
            .value(is(DEFAULT_CORREO_PERSONAL))
            .jsonPath("$.nacionalidad")
            .value(is(DEFAULT_NACIONALIDAD));
    }

    @Test
    void getNonExistingInfoPersonal() {
        // Get the infoPersonal
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingInfoPersonal() throws Exception {
        // Initialize the database
        infoPersonal.setId(UUID.randomUUID().toString());
        infoPersonalRepository.save(infoPersonal).block();

        int databaseSizeBeforeUpdate = infoPersonalRepository.findAll().collectList().block().size();

        // Update the infoPersonal
        InfoPersonal updatedInfoPersonal = infoPersonalRepository.findById(infoPersonal.getId()).block();
        updatedInfoPersonal
            .tipoID(UPDATED_TIPO_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .genero(UPDATED_GENERO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .edadIngreso(UPDATED_EDAD_INGRESO)
            .telefono(UPDATED_TELEFONO)
            .celular(UPDATED_CELULAR)
            .correoPersonal(UPDATED_CORREO_PERSONAL)
            .nacionalidad(UPDATED_NACIONALIDAD);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedInfoPersonal.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedInfoPersonal))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeUpdate);
        InfoPersonal testInfoPersonal = infoPersonalList.get(infoPersonalList.size() - 1);
        assertThat(testInfoPersonal.getTipoID()).isEqualTo(UPDATED_TIPO_ID);
        assertThat(testInfoPersonal.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInfoPersonal.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testInfoPersonal.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testInfoPersonal.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testInfoPersonal.getEdadIngreso()).isEqualTo(UPDATED_EDAD_INGRESO);
        assertThat(testInfoPersonal.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testInfoPersonal.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testInfoPersonal.getCorreoPersonal()).isEqualTo(UPDATED_CORREO_PERSONAL);
        assertThat(testInfoPersonal.getNacionalidad()).isEqualTo(UPDATED_NACIONALIDAD);
    }

    @Test
    void putNonExistingInfoPersonal() throws Exception {
        int databaseSizeBeforeUpdate = infoPersonalRepository.findAll().collectList().block().size();
        infoPersonal.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, infoPersonal.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoPersonal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchInfoPersonal() throws Exception {
        int databaseSizeBeforeUpdate = infoPersonalRepository.findAll().collectList().block().size();
        infoPersonal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoPersonal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamInfoPersonal() throws Exception {
        int databaseSizeBeforeUpdate = infoPersonalRepository.findAll().collectList().block().size();
        infoPersonal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoPersonal))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateInfoPersonalWithPatch() throws Exception {
        // Initialize the database
        infoPersonal.setId(UUID.randomUUID().toString());
        infoPersonalRepository.save(infoPersonal).block();

        int databaseSizeBeforeUpdate = infoPersonalRepository.findAll().collectList().block().size();

        // Update the infoPersonal using partial update
        InfoPersonal partialUpdatedInfoPersonal = new InfoPersonal();
        partialUpdatedInfoPersonal.setId(infoPersonal.getId());

        partialUpdatedInfoPersonal
            .tipoID(UPDATED_TIPO_ID)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .edadIngreso(UPDATED_EDAD_INGRESO)
            .telefono(UPDATED_TELEFONO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedInfoPersonal.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoPersonal))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeUpdate);
        InfoPersonal testInfoPersonal = infoPersonalList.get(infoPersonalList.size() - 1);
        assertThat(testInfoPersonal.getTipoID()).isEqualTo(UPDATED_TIPO_ID);
        assertThat(testInfoPersonal.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testInfoPersonal.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testInfoPersonal.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testInfoPersonal.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testInfoPersonal.getEdadIngreso()).isEqualTo(UPDATED_EDAD_INGRESO);
        assertThat(testInfoPersonal.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testInfoPersonal.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testInfoPersonal.getCorreoPersonal()).isEqualTo(DEFAULT_CORREO_PERSONAL);
        assertThat(testInfoPersonal.getNacionalidad()).isEqualTo(DEFAULT_NACIONALIDAD);
    }

    @Test
    void fullUpdateInfoPersonalWithPatch() throws Exception {
        // Initialize the database
        infoPersonal.setId(UUID.randomUUID().toString());
        infoPersonalRepository.save(infoPersonal).block();

        int databaseSizeBeforeUpdate = infoPersonalRepository.findAll().collectList().block().size();

        // Update the infoPersonal using partial update
        InfoPersonal partialUpdatedInfoPersonal = new InfoPersonal();
        partialUpdatedInfoPersonal.setId(infoPersonal.getId());

        partialUpdatedInfoPersonal
            .tipoID(UPDATED_TIPO_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .genero(UPDATED_GENERO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .edadIngreso(UPDATED_EDAD_INGRESO)
            .telefono(UPDATED_TELEFONO)
            .celular(UPDATED_CELULAR)
            .correoPersonal(UPDATED_CORREO_PERSONAL)
            .nacionalidad(UPDATED_NACIONALIDAD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedInfoPersonal.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoPersonal))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeUpdate);
        InfoPersonal testInfoPersonal = infoPersonalList.get(infoPersonalList.size() - 1);
        assertThat(testInfoPersonal.getTipoID()).isEqualTo(UPDATED_TIPO_ID);
        assertThat(testInfoPersonal.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInfoPersonal.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testInfoPersonal.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testInfoPersonal.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testInfoPersonal.getEdadIngreso()).isEqualTo(UPDATED_EDAD_INGRESO);
        assertThat(testInfoPersonal.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testInfoPersonal.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testInfoPersonal.getCorreoPersonal()).isEqualTo(UPDATED_CORREO_PERSONAL);
        assertThat(testInfoPersonal.getNacionalidad()).isEqualTo(UPDATED_NACIONALIDAD);
    }

    @Test
    void patchNonExistingInfoPersonal() throws Exception {
        int databaseSizeBeforeUpdate = infoPersonalRepository.findAll().collectList().block().size();
        infoPersonal.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, infoPersonal.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoPersonal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchInfoPersonal() throws Exception {
        int databaseSizeBeforeUpdate = infoPersonalRepository.findAll().collectList().block().size();
        infoPersonal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoPersonal))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamInfoPersonal() throws Exception {
        int databaseSizeBeforeUpdate = infoPersonalRepository.findAll().collectList().block().size();
        infoPersonal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(infoPersonal))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the InfoPersonal in the database
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteInfoPersonal() {
        // Initialize the database
        infoPersonal.setId(UUID.randomUUID().toString());
        infoPersonalRepository.save(infoPersonal).block();

        int databaseSizeBeforeDelete = infoPersonalRepository.findAll().collectList().block().size();

        // Delete the infoPersonal
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, infoPersonal.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<InfoPersonal> infoPersonalList = infoPersonalRepository.findAll().collectList().block();
        assertThat(infoPersonalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
