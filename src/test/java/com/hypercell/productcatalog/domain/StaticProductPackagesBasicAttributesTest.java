package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductPackagesBasicAttributesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProductPackagesBasicAttributes.class);
        StaticProductPackagesBasicAttributes staticProductPackagesBasicAttributes1 = new StaticProductPackagesBasicAttributes();
        staticProductPackagesBasicAttributes1.setId(1L);
        StaticProductPackagesBasicAttributes staticProductPackagesBasicAttributes2 = new StaticProductPackagesBasicAttributes();
        staticProductPackagesBasicAttributes2.setId(staticProductPackagesBasicAttributes1.getId());
        assertThat(staticProductPackagesBasicAttributes1).isEqualTo(staticProductPackagesBasicAttributes2);
        staticProductPackagesBasicAttributes2.setId(2L);
        assertThat(staticProductPackagesBasicAttributes1).isNotEqualTo(staticProductPackagesBasicAttributes2);
        staticProductPackagesBasicAttributes1.setId(null);
        assertThat(staticProductPackagesBasicAttributes1).isNotEqualTo(staticProductPackagesBasicAttributes2);
    }
}
