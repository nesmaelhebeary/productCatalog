package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class ConditionDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConditionDetails.class);
        ConditionDetails conditionDetails1 = new ConditionDetails();
        conditionDetails1.setId(1L);
        ConditionDetails conditionDetails2 = new ConditionDetails();
        conditionDetails2.setId(conditionDetails1.getId());
        assertThat(conditionDetails1).isEqualTo(conditionDetails2);
        conditionDetails2.setId(2L);
        assertThat(conditionDetails1).isNotEqualTo(conditionDetails2);
        conditionDetails1.setId(null);
        assertThat(conditionDetails1).isNotEqualTo(conditionDetails2);
    }
}
