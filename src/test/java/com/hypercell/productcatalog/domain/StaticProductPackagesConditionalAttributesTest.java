package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductPackagesConditionalAttributesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProductPackagesConditionalAttributes.class);
        StaticProductPackagesConditionalAttributes staticProductPackagesConditionalAttributes1 = new StaticProductPackagesConditionalAttributes();
        staticProductPackagesConditionalAttributes1.setId(1L);
        StaticProductPackagesConditionalAttributes staticProductPackagesConditionalAttributes2 = new StaticProductPackagesConditionalAttributes();
        staticProductPackagesConditionalAttributes2.setId(staticProductPackagesConditionalAttributes1.getId());
        assertThat(staticProductPackagesConditionalAttributes1).isEqualTo(staticProductPackagesConditionalAttributes2);
        staticProductPackagesConditionalAttributes2.setId(2L);
        assertThat(staticProductPackagesConditionalAttributes1).isNotEqualTo(staticProductPackagesConditionalAttributes2);
        staticProductPackagesConditionalAttributes1.setId(null);
        assertThat(staticProductPackagesConditionalAttributes1).isNotEqualTo(staticProductPackagesConditionalAttributes2);
    }
}
