package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductBasicAttributesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProductBasicAttributes.class);
        StaticProductBasicAttributes staticProductBasicAttributes1 = new StaticProductBasicAttributes();
        staticProductBasicAttributes1.setId(1L);
        StaticProductBasicAttributes staticProductBasicAttributes2 = new StaticProductBasicAttributes();
        staticProductBasicAttributes2.setId(staticProductBasicAttributes1.getId());
        assertThat(staticProductBasicAttributes1).isEqualTo(staticProductBasicAttributes2);
        staticProductBasicAttributes2.setId(2L);
        assertThat(staticProductBasicAttributes1).isNotEqualTo(staticProductBasicAttributes2);
        staticProductBasicAttributes1.setId(null);
        assertThat(staticProductBasicAttributes1).isNotEqualTo(staticProductBasicAttributes2);
    }
}
