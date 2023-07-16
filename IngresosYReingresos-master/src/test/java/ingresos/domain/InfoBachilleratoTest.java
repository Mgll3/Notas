package ingresos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ingresos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfoBachilleratoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoBachillerato.class);
        InfoBachillerato infoBachillerato1 = new InfoBachillerato();
        infoBachillerato1.setId(1L);
        InfoBachillerato infoBachillerato2 = new InfoBachillerato();
        infoBachillerato2.setId(infoBachillerato1.getId());
        assertThat(infoBachillerato1).isEqualTo(infoBachillerato2);
        infoBachillerato2.setId(2L);
        assertThat(infoBachillerato1).isNotEqualTo(infoBachillerato2);
        infoBachillerato1.setId(null);
        assertThat(infoBachillerato1).isNotEqualTo(infoBachillerato2);
    }
}
