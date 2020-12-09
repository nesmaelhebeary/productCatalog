package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductFamilyEligibiltyRulesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProductFamilyEligibiltyRules.class);
        StaticProductFamilyEligibiltyRules staticProductFamilyEligibiltyRules1 = new StaticProductFamilyEligibiltyRules();
        staticProductFamilyEligibiltyRules1.setId(1L);
        StaticProductFamilyEligibiltyRules staticProductFamilyEligibiltyRules2 = new StaticProductFamilyEligibiltyRules();
        staticProductFamilyEligibiltyRules2.setId(staticProductFamilyEligibiltyRules1.getId());
        assertThat(staticProductFamilyEligibiltyRules1).isEqualTo(staticProductFamilyEligibiltyRules2);
        staticProductFamilyEligibiltyRules2.setId(2L);
        assertThat(staticProductFamilyEligibiltyRules1).isNotEqualTo(staticProductFamilyEligibiltyRules2);
        staticProductFamilyEligibiltyRules1.setId(null);
        assertThat(staticProductFamilyEligibiltyRules1).isNotEqualTo(staticProductFamilyEligibiltyRules2);
    }
}
