package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class ExternalSystemsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalSystems.class);
        ExternalSystems externalSystems1 = new ExternalSystems();
        externalSystems1.setId(1L);
        ExternalSystems externalSystems2 = new ExternalSystems();
        externalSystems2.setId(externalSystems1.getId());
        assertThat(externalSystems1).isEqualTo(externalSystems2);
        externalSystems2.setId(2L);
        assertThat(externalSystems1).isNotEqualTo(externalSystems2);
        externalSystems1.setId(null);
        assertThat(externalSystems1).isNotEqualTo(externalSystems2);
    }
}
