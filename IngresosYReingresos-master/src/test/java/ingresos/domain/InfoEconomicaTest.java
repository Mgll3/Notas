package ingresos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ingresos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfoEconomicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoEconomica.class);
        InfoEconomica infoEconomica1 = new InfoEconomica();
        infoEconomica1.setId(1L);
        InfoEconomica infoEconomica2 = new InfoEconomica();
        infoEconomica2.setId(infoEconomica1.getId());
        assertThat(infoEconomica1).isEqualTo(infoEconomica2);
        infoEconomica2.setId(2L);
        assertThat(infoEconomica1).isNotEqualTo(infoEconomica2);
        infoEconomica1.setId(null);
        assertThat(infoEconomica1).isNotEqualTo(infoEconomica2);
    }
}
