package ingresos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ingresos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfoAcademicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoAcademica.class);
        InfoAcademica infoAcademica1 = new InfoAcademica();
        infoAcademica1.setId(1L);
        InfoAcademica infoAcademica2 = new InfoAcademica();
        infoAcademica2.setId(infoAcademica1.getId());
        assertThat(infoAcademica1).isEqualTo(infoAcademica2);
        infoAcademica2.setId(2L);
        assertThat(infoAcademica1).isNotEqualTo(infoAcademica2);
        infoAcademica1.setId(null);
        assertThat(infoAcademica1).isNotEqualTo(infoAcademica2);
    }
}
