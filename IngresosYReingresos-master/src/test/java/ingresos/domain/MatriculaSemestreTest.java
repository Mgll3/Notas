package ingresos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ingresos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatriculaSemestreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatriculaSemestre.class);
        MatriculaSemestre matriculaSemestre1 = new MatriculaSemestre();
        matriculaSemestre1.setId(1L);
        MatriculaSemestre matriculaSemestre2 = new MatriculaSemestre();
        matriculaSemestre2.setId(matriculaSemestre1.getId());
        assertThat(matriculaSemestre1).isEqualTo(matriculaSemestre2);
        matriculaSemestre2.setId(2L);
        assertThat(matriculaSemestre1).isNotEqualTo(matriculaSemestre2);
        matriculaSemestre1.setId(null);
        assertThat(matriculaSemestre1).isNotEqualTo(matriculaSemestre2);
    }
}
