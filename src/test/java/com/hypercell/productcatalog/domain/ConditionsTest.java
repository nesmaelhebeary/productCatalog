package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class ConditionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conditions.class);
        Conditions conditions1 = new Conditions();
        conditions1.setId(1L);
        Conditions conditions2 = new Conditions();
        conditions2.setId(conditions1.getId());
        assertThat(conditions1).isEqualTo(conditions2);
        conditions2.setId(2L);
        assertThat(conditions1).isNotEqualTo(conditions2);
        conditions1.setId(null);
        assertThat(conditions1).isNotEqualTo(conditions2);
    }
}
