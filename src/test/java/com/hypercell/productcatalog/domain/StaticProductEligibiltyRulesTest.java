package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductEligibiltyRulesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProductEligibiltyRules.class);
        StaticProductEligibiltyRules staticProductEligibiltyRules1 = new StaticProductEligibiltyRules();
        staticProductEligibiltyRules1.setId(1L);
        StaticProductEligibiltyRules staticProductEligibiltyRules2 = new StaticProductEligibiltyRules();
        staticProductEligibiltyRules2.setId(staticProductEligibiltyRules1.getId());
        assertThat(staticProductEligibiltyRules1).isEqualTo(staticProductEligibiltyRules2);
        staticProductEligibiltyRules2.setId(2L);
        assertThat(staticProductEligibiltyRules1).isNotEqualTo(staticProductEligibiltyRules2);
        staticProductEligibiltyRules1.setId(null);
        assertThat(staticProductEligibiltyRules1).isNotEqualTo(staticProductEligibiltyRules2);
    }
}
