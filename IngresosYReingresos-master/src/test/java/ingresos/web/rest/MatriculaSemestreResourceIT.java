package ingresos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import ingresos.IntegrationTest;
import ingresos.domain.InfoAcademica;
import ingresos.domain.MatriculaSemestre;
import ingresos.domain.Programa;
import ingresos.repository.EntityManager;
import ingresos.repository.MatriculaSemestreRepository;
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
 * Integration tests for the {@link MatriculaSemestreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class MatriculaSemestreResourceIT {

    private static final String DEFAULT_TIPO_INGRESO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_INGRESO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_ASPIRANTE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ASPIRANTE = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_MODALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_MODALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_SEDE = "AAAAAAAAAA";
    private static final String UPDATED_SEDE = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_COHORTE = "AAAAAAAAAA";
    private static final String UPDATED_COHORTE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Double DEFAULT_PROMEDIO_SEMESTRE = 1D;
    private static final Double UPDATED_PROMEDIO_SEMESTRE = 2D;

    private static final Double DEFAULT_PROMEDIO_ACUMULADO = 1D;
    private static final Double UPDATED_PROMEDIO_ACUMULADO = 2D;

    private static final String DEFAULT_LIQUIDACION_MATRICULA = "AAAAAAAAAA";
    private static final String UPDATED_LIQUIDACION_MATRICULA = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_PAGO = "BBBBBBBBBB";

    private static final String DEFAULT_SITUACION_ACADEMICA = "AAAAAAAAAA";
    private static final String UPDATED_SITUACION_ACADEMICA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/matricula-semestres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MatriculaSemestreRepository matriculaSemestreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private MatriculaSemestre matriculaSemestre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatriculaSemestre createEntity(EntityManager em) {
        MatriculaSemestre matriculaSemestre = new MatriculaSemestre()
            .tipoIngreso(DEFAULT_TIPO_INGRESO)
            .tipoAspirante(DEFAULT_TIPO_ASPIRANTE)
            .version(DEFAULT_VERSION)
            .modalidad(DEFAULT_MODALIDAD)
            .sede(DEFAULT_SEDE)
            .year(DEFAULT_YEAR)
            .cohorte(DEFAULT_COHORTE)
            .estado(DEFAULT_ESTADO)
            .promedioSemestre(DEFAULT_PROMEDIO_SEMESTRE)
            .promedioAcumulado(DEFAULT_PROMEDIO_ACUMULADO)
            .liquidacionMatricula(DEFAULT_LIQUIDACION_MATRICULA)
            .estadoPago(DEFAULT_ESTADO_PAGO)
            .situacionAcademica(DEFAULT_SITUACION_ACADEMICA);
        // Add required entity
        Programa programa;
        programa = em.insert(ProgramaResourceIT.createEntity(em)).block();
        matriculaSemestre.setMatriculaS(programa);
        // Add required entity
        InfoAcademica infoAcademica;
        infoAcademica = em.insert(InfoAcademicaResourceIT.createEntity(em)).block();
        matriculaSemestre.setMatriculaA(infoAcademica);
        return matriculaSemestre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatriculaSemestre createUpdatedEntity(EntityManager em) {
        MatriculaSemestre matriculaSemestre = new MatriculaSemestre()
            .tipoIngreso(UPDATED_TIPO_INGRESO)
            .tipoAspirante(UPDATED_TIPO_ASPIRANTE)
            .version(UPDATED_VERSION)
            .modalidad(UPDATED_MODALIDAD)
            .sede(UPDATED_SEDE)
            .year(UPDATED_YEAR)
            .cohorte(UPDATED_COHORTE)
            .estado(UPDATED_ESTADO)
            .promedioSemestre(UPDATED_PROMEDIO_SEMESTRE)
            .promedioAcumulado(UPDATED_PROMEDIO_ACUMULADO)
            .liquidacionMatricula(UPDATED_LIQUIDACION_MATRICULA)
            .estadoPago(UPDATED_ESTADO_PAGO)
            .situacionAcademica(UPDATED_SITUACION_ACADEMICA);
        // Add required entity
        Programa programa;
        programa = em.insert(ProgramaResourceIT.createUpdatedEntity(em)).block();
        matriculaSemestre.setMatriculaS(programa);
        // Add required entity
        InfoAcademica infoAcademica;
        infoAcademica = em.insert(InfoAcademicaResourceIT.createUpdatedEntity(em)).block();
        matriculaSemestre.setMatriculaA(infoAcademica);
        return matriculaSemestre;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(MatriculaSemestre.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        ProgramaResourceIT.deleteEntities(em);
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
        matriculaSemestre = createEntity(em);
    }

    @Test
    void createMatriculaSemestre() throws Exception {
        int databaseSizeBeforeCreate = matriculaSemestreRepository.findAll().collectList().block().size();
        // Create the MatriculaSemestre
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(matriculaSemestre))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeCreate + 1);
        MatriculaSemestre testMatriculaSemestre = matriculaSemestreList.get(matriculaSemestreList.size() - 1);
        assertThat(testMatriculaSemestre.getTipoIngreso()).isEqualTo(DEFAULT_TIPO_INGRESO);
        assertThat(testMatriculaSemestre.getTipoAspirante()).isEqualTo(DEFAULT_TIPO_ASPIRANTE);
        assertThat(testMatriculaSemestre.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testMatriculaSemestre.getModalidad()).isEqualTo(DEFAULT_MODALIDAD);
        assertThat(testMatriculaSemestre.getSede()).isEqualTo(DEFAULT_SEDE);
        assertThat(testMatriculaSemestre.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMatriculaSemestre.getCohorte()).isEqualTo(DEFAULT_COHORTE);
        assertThat(testMatriculaSemestre.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMatriculaSemestre.getPromedioSemestre()).isEqualTo(DEFAULT_PROMEDIO_SEMESTRE);
        assertThat(testMatriculaSemestre.getPromedioAcumulado()).isEqualTo(DEFAULT_PROMEDIO_ACUMULADO);
        assertThat(testMatriculaSemestre.getLiquidacionMatricula()).isEqualTo(DEFAULT_LIQUIDACION_MATRICULA);
        assertThat(testMatriculaSemestre.getEstadoPago()).isEqualTo(DEFAULT_ESTADO_PAGO);
        assertThat(testMatriculaSemestre.getSituacionAcademica()).isEqualTo(DEFAULT_SITUACION_ACADEMICA);
    }

    @Test
    void createMatriculaSemestreWithExistingId() throws Exception {
        // Create the MatriculaSemestre with an existing ID
        matriculaSemestre.setId(1L);

        int databaseSizeBeforeCreate = matriculaSemestreRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(matriculaSemestre))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMatriculaSemestresAsStream() {
        // Initialize the database
        matriculaSemestreRepository.save(matriculaSemestre).block();

        List<MatriculaSemestre> matriculaSemestreList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(MatriculaSemestre.class)
            .getResponseBody()
            .filter(matriculaSemestre::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(matriculaSemestreList).isNotNull();
        assertThat(matriculaSemestreList).hasSize(1);
        MatriculaSemestre testMatriculaSemestre = matriculaSemestreList.get(0);
        assertThat(testMatriculaSemestre.getTipoIngreso()).isEqualTo(DEFAULT_TIPO_INGRESO);
        assertThat(testMatriculaSemestre.getTipoAspirante()).isEqualTo(DEFAULT_TIPO_ASPIRANTE);
        assertThat(testMatriculaSemestre.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testMatriculaSemestre.getModalidad()).isEqualTo(DEFAULT_MODALIDAD);
        assertThat(testMatriculaSemestre.getSede()).isEqualTo(DEFAULT_SEDE);
        assertThat(testMatriculaSemestre.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMatriculaSemestre.getCohorte()).isEqualTo(DEFAULT_COHORTE);
        assertThat(testMatriculaSemestre.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMatriculaSemestre.getPromedioSemestre()).isEqualTo(DEFAULT_PROMEDIO_SEMESTRE);
        assertThat(testMatriculaSemestre.getPromedioAcumulado()).isEqualTo(DEFAULT_PROMEDIO_ACUMULADO);
        assertThat(testMatriculaSemestre.getLiquidacionMatricula()).isEqualTo(DEFAULT_LIQUIDACION_MATRICULA);
        assertThat(testMatriculaSemestre.getEstadoPago()).isEqualTo(DEFAULT_ESTADO_PAGO);
        assertThat(testMatriculaSemestre.getSituacionAcademica()).isEqualTo(DEFAULT_SITUACION_ACADEMICA);
    }

    @Test
    void getAllMatriculaSemestres() {
        // Initialize the database
        matriculaSemestreRepository.save(matriculaSemestre).block();

        // Get all the matriculaSemestreList
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
            .value(hasItem(matriculaSemestre.getId().intValue()))
            .jsonPath("$.[*].tipoIngreso")
            .value(hasItem(DEFAULT_TIPO_INGRESO))
            .jsonPath("$.[*].tipoAspirante")
            .value(hasItem(DEFAULT_TIPO_ASPIRANTE))
            .jsonPath("$.[*].version")
            .value(hasItem(DEFAULT_VERSION))
            .jsonPath("$.[*].modalidad")
            .value(hasItem(DEFAULT_MODALIDAD))
            .jsonPath("$.[*].sede")
            .value(hasItem(DEFAULT_SEDE))
            .jsonPath("$.[*].year")
            .value(hasItem(DEFAULT_YEAR))
            .jsonPath("$.[*].cohorte")
            .value(hasItem(DEFAULT_COHORTE))
            .jsonPath("$.[*].estado")
            .value(hasItem(DEFAULT_ESTADO))
            .jsonPath("$.[*].promedioSemestre")
            .value(hasItem(DEFAULT_PROMEDIO_SEMESTRE.doubleValue()))
            .jsonPath("$.[*].promedioAcumulado")
            .value(hasItem(DEFAULT_PROMEDIO_ACUMULADO.doubleValue()))
            .jsonPath("$.[*].liquidacionMatricula")
            .value(hasItem(DEFAULT_LIQUIDACION_MATRICULA))
            .jsonPath("$.[*].estadoPago")
            .value(hasItem(DEFAULT_ESTADO_PAGO))
            .jsonPath("$.[*].situacionAcademica")
            .value(hasItem(DEFAULT_SITUACION_ACADEMICA));
    }

    @Test
    void getMatriculaSemestre() {
        // Initialize the database
        matriculaSemestreRepository.save(matriculaSemestre).block();

        // Get the matriculaSemestre
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, matriculaSemestre.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(matriculaSemestre.getId().intValue()))
            .jsonPath("$.tipoIngreso")
            .value(is(DEFAULT_TIPO_INGRESO))
            .jsonPath("$.tipoAspirante")
            .value(is(DEFAULT_TIPO_ASPIRANTE))
            .jsonPath("$.version")
            .value(is(DEFAULT_VERSION))
            .jsonPath("$.modalidad")
            .value(is(DEFAULT_MODALIDAD))
            .jsonPath("$.sede")
            .value(is(DEFAULT_SEDE))
            .jsonPath("$.year")
            .value(is(DEFAULT_YEAR))
            .jsonPath("$.cohorte")
            .value(is(DEFAULT_COHORTE))
            .jsonPath("$.estado")
            .value(is(DEFAULT_ESTADO))
            .jsonPath("$.promedioSemestre")
            .value(is(DEFAULT_PROMEDIO_SEMESTRE.doubleValue()))
            .jsonPath("$.promedioAcumulado")
            .value(is(DEFAULT_PROMEDIO_ACUMULADO.doubleValue()))
            .jsonPath("$.liquidacionMatricula")
            .value(is(DEFAULT_LIQUIDACION_MATRICULA))
            .jsonPath("$.estadoPago")
            .value(is(DEFAULT_ESTADO_PAGO))
            .jsonPath("$.situacionAcademica")
            .value(is(DEFAULT_SITUACION_ACADEMICA));
    }

    @Test
    void getNonExistingMatriculaSemestre() {
        // Get the matriculaSemestre
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingMatriculaSemestre() throws Exception {
        // Initialize the database
        matriculaSemestreRepository.save(matriculaSemestre).block();

        int databaseSizeBeforeUpdate = matriculaSemestreRepository.findAll().collectList().block().size();

        // Update the matriculaSemestre
        MatriculaSemestre updatedMatriculaSemestre = matriculaSemestreRepository.findById(matriculaSemestre.getId()).block();
        updatedMatriculaSemestre
            .tipoIngreso(UPDATED_TIPO_INGRESO)
            .tipoAspirante(UPDATED_TIPO_ASPIRANTE)
            .version(UPDATED_VERSION)
            .modalidad(UPDATED_MODALIDAD)
            .sede(UPDATED_SEDE)
            .year(UPDATED_YEAR)
            .cohorte(UPDATED_COHORTE)
            .estado(UPDATED_ESTADO)
            .promedioSemestre(UPDATED_PROMEDIO_SEMESTRE)
            .promedioAcumulado(UPDATED_PROMEDIO_ACUMULADO)
            .liquidacionMatricula(UPDATED_LIQUIDACION_MATRICULA)
            .estadoPago(UPDATED_ESTADO_PAGO)
            .situacionAcademica(UPDATED_SITUACION_ACADEMICA);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedMatriculaSemestre.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedMatriculaSemestre))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeUpdate);
        MatriculaSemestre testMatriculaSemestre = matriculaSemestreList.get(matriculaSemestreList.size() - 1);
        assertThat(testMatriculaSemestre.getTipoIngreso()).isEqualTo(UPDATED_TIPO_INGRESO);
        assertThat(testMatriculaSemestre.getTipoAspirante()).isEqualTo(UPDATED_TIPO_ASPIRANTE);
        assertThat(testMatriculaSemestre.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testMatriculaSemestre.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
        assertThat(testMatriculaSemestre.getSede()).isEqualTo(UPDATED_SEDE);
        assertThat(testMatriculaSemestre.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMatriculaSemestre.getCohorte()).isEqualTo(UPDATED_COHORTE);
        assertThat(testMatriculaSemestre.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMatriculaSemestre.getPromedioSemestre()).isEqualTo(UPDATED_PROMEDIO_SEMESTRE);
        assertThat(testMatriculaSemestre.getPromedioAcumulado()).isEqualTo(UPDATED_PROMEDIO_ACUMULADO);
        assertThat(testMatriculaSemestre.getLiquidacionMatricula()).isEqualTo(UPDATED_LIQUIDACION_MATRICULA);
        assertThat(testMatriculaSemestre.getEstadoPago()).isEqualTo(UPDATED_ESTADO_PAGO);
        assertThat(testMatriculaSemestre.getSituacionAcademica()).isEqualTo(UPDATED_SITUACION_ACADEMICA);
    }

    @Test
    void putNonExistingMatriculaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = matriculaSemestreRepository.findAll().collectList().block().size();
        matriculaSemestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, matriculaSemestre.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(matriculaSemestre))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMatriculaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = matriculaSemestreRepository.findAll().collectList().block().size();
        matriculaSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(matriculaSemestre))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMatriculaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = matriculaSemestreRepository.findAll().collectList().block().size();
        matriculaSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(matriculaSemestre))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMatriculaSemestreWithPatch() throws Exception {
        // Initialize the database
        matriculaSemestreRepository.save(matriculaSemestre).block();

        int databaseSizeBeforeUpdate = matriculaSemestreRepository.findAll().collectList().block().size();

        // Update the matriculaSemestre using partial update
        MatriculaSemestre partialUpdatedMatriculaSemestre = new MatriculaSemestre();
        partialUpdatedMatriculaSemestre.setId(matriculaSemestre.getId());

        partialUpdatedMatriculaSemestre
            .tipoIngreso(UPDATED_TIPO_INGRESO)
            .modalidad(UPDATED_MODALIDAD)
            .sede(UPDATED_SEDE)
            .situacionAcademica(UPDATED_SITUACION_ACADEMICA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMatriculaSemestre.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMatriculaSemestre))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeUpdate);
        MatriculaSemestre testMatriculaSemestre = matriculaSemestreList.get(matriculaSemestreList.size() - 1);
        assertThat(testMatriculaSemestre.getTipoIngreso()).isEqualTo(UPDATED_TIPO_INGRESO);
        assertThat(testMatriculaSemestre.getTipoAspirante()).isEqualTo(DEFAULT_TIPO_ASPIRANTE);
        assertThat(testMatriculaSemestre.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testMatriculaSemestre.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
        assertThat(testMatriculaSemestre.getSede()).isEqualTo(UPDATED_SEDE);
        assertThat(testMatriculaSemestre.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMatriculaSemestre.getCohorte()).isEqualTo(DEFAULT_COHORTE);
        assertThat(testMatriculaSemestre.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMatriculaSemestre.getPromedioSemestre()).isEqualTo(DEFAULT_PROMEDIO_SEMESTRE);
        assertThat(testMatriculaSemestre.getPromedioAcumulado()).isEqualTo(DEFAULT_PROMEDIO_ACUMULADO);
        assertThat(testMatriculaSemestre.getLiquidacionMatricula()).isEqualTo(DEFAULT_LIQUIDACION_MATRICULA);
        assertThat(testMatriculaSemestre.getEstadoPago()).isEqualTo(DEFAULT_ESTADO_PAGO);
        assertThat(testMatriculaSemestre.getSituacionAcademica()).isEqualTo(UPDATED_SITUACION_ACADEMICA);
    }

    @Test
    void fullUpdateMatriculaSemestreWithPatch() throws Exception {
        // Initialize the database
        matriculaSemestreRepository.save(matriculaSemestre).block();

        int databaseSizeBeforeUpdate = matriculaSemestreRepository.findAll().collectList().block().size();

        // Update the matriculaSemestre using partial update
        MatriculaSemestre partialUpdatedMatriculaSemestre = new MatriculaSemestre();
        partialUpdatedMatriculaSemestre.setId(matriculaSemestre.getId());

        partialUpdatedMatriculaSemestre
            .tipoIngreso(UPDATED_TIPO_INGRESO)
            .tipoAspirante(UPDATED_TIPO_ASPIRANTE)
            .version(UPDATED_VERSION)
            .modalidad(UPDATED_MODALIDAD)
            .sede(UPDATED_SEDE)
            .year(UPDATED_YEAR)
            .cohorte(UPDATED_COHORTE)
            .estado(UPDATED_ESTADO)
            .promedioSemestre(UPDATED_PROMEDIO_SEMESTRE)
            .promedioAcumulado(UPDATED_PROMEDIO_ACUMULADO)
            .liquidacionMatricula(UPDATED_LIQUIDACION_MATRICULA)
            .estadoPago(UPDATED_ESTADO_PAGO)
            .situacionAcademica(UPDATED_SITUACION_ACADEMICA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMatriculaSemestre.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMatriculaSemestre))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeUpdate);
        MatriculaSemestre testMatriculaSemestre = matriculaSemestreList.get(matriculaSemestreList.size() - 1);
        assertThat(testMatriculaSemestre.getTipoIngreso()).isEqualTo(UPDATED_TIPO_INGRESO);
        assertThat(testMatriculaSemestre.getTipoAspirante()).isEqualTo(UPDATED_TIPO_ASPIRANTE);
        assertThat(testMatriculaSemestre.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testMatriculaSemestre.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
        assertThat(testMatriculaSemestre.getSede()).isEqualTo(UPDATED_SEDE);
        assertThat(testMatriculaSemestre.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMatriculaSemestre.getCohorte()).isEqualTo(UPDATED_COHORTE);
        assertThat(testMatriculaSemestre.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMatriculaSemestre.getPromedioSemestre()).isEqualTo(UPDATED_PROMEDIO_SEMESTRE);
        assertThat(testMatriculaSemestre.getPromedioAcumulado()).isEqualTo(UPDATED_PROMEDIO_ACUMULADO);
        assertThat(testMatriculaSemestre.getLiquidacionMatricula()).isEqualTo(UPDATED_LIQUIDACION_MATRICULA);
        assertThat(testMatriculaSemestre.getEstadoPago()).isEqualTo(UPDATED_ESTADO_PAGO);
        assertThat(testMatriculaSemestre.getSituacionAcademica()).isEqualTo(UPDATED_SITUACION_ACADEMICA);
    }

    @Test
    void patchNonExistingMatriculaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = matriculaSemestreRepository.findAll().collectList().block().size();
        matriculaSemestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, matriculaSemestre.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(matriculaSemestre))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMatriculaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = matriculaSemestreRepository.findAll().collectList().block().size();
        matriculaSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(matriculaSemestre))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMatriculaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = matriculaSemestreRepository.findAll().collectList().block().size();
        matriculaSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(matriculaSemestre))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the MatriculaSemestre in the database
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMatriculaSemestre() {
        // Initialize the database
        matriculaSemestreRepository.save(matriculaSemestre).block();

        int databaseSizeBeforeDelete = matriculaSemestreRepository.findAll().collectList().block().size();

        // Delete the matriculaSemestre
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, matriculaSemestre.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<MatriculaSemestre> matriculaSemestreList = matriculaSemestreRepository.findAll().collectList().block();
        assertThat(matriculaSemestreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
