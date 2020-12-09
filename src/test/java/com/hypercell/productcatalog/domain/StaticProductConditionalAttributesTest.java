package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductConditionalAttributesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProductConditionalAttributes.class);
        StaticProductConditionalAttributes staticProductConditionalAttributes1 = new StaticProductConditionalAttributes();
        staticProductConditionalAttributes1.setId(1L);
        StaticProductConditionalAttributes staticProductConditionalAttributes2 = new StaticProductConditionalAttributes();
        staticProductConditionalAttributes2.setId(staticProductConditionalAttributes1.getId());
        assertThat(staticProductConditionalAttributes1).isEqualTo(staticProductConditionalAttributes2);
        staticProductConditionalAttributes2.setId(2L);
        assertThat(staticProductConditionalAttributes1).isNotEqualTo(staticProductConditionalAttributes2);
        staticProductConditionalAttributes1.setId(null);
        assertThat(staticProductConditionalAttributes1).isNotEqualTo(staticProductConditionalAttributes2);
    }
}
