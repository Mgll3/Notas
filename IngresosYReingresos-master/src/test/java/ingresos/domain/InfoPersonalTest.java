package ingresos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ingresos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfoPersonalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoPersonal.class);
        InfoPersonal infoPersonal1 = new InfoPersonal();
        infoPersonal1.setId("id1");
        InfoPersonal infoPersonal2 = new InfoPersonal();
        infoPersonal2.setId(infoPersonal1.getId());
        assertThat(infoPersonal1).isEqualTo(infoPersonal2);
        infoPersonal2.setId("id2");
        assertThat(infoPersonal1).isNotEqualTo(infoPersonal2);
        infoPersonal1.setId(null);
        assertThat(infoPersonal1).isNotEqualTo(infoPersonal2);
    }
}
