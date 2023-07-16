package ingresos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ingresos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtroEstudioFormalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtroEstudioFormal.class);
        OtroEstudioFormal otroEstudioFormal1 = new OtroEstudioFormal();
        otroEstudioFormal1.setId(1L);
        OtroEstudioFormal otroEstudioFormal2 = new OtroEstudioFormal();
        otroEstudioFormal2.setId(otroEstudioFormal1.getId());
        assertThat(otroEstudioFormal1).isEqualTo(otroEstudioFormal2);
        otroEstudioFormal2.setId(2L);
        assertThat(otroEstudioFormal1).isNotEqualTo(otroEstudioFormal2);
        otroEstudioFormal1.setId(null);
        assertThat(otroEstudioFormal1).isNotEqualTo(otroEstudioFormal2);
    }
}
