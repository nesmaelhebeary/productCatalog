package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class DynamicProductsOutputTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DynamicProductsOutput.class);
        DynamicProductsOutput dynamicProductsOutput1 = new DynamicProductsOutput();
        dynamicProductsOutput1.setId(1L);
        DynamicProductsOutput dynamicProductsOutput2 = new DynamicProductsOutput();
        dynamicProductsOutput2.setId(dynamicProductsOutput1.getId());
        assertThat(dynamicProductsOutput1).isEqualTo(dynamicProductsOutput2);
        dynamicProductsOutput2.setId(2L);
        assertThat(dynamicProductsOutput1).isNotEqualTo(dynamicProductsOutput2);
        dynamicProductsOutput1.setId(null);
        assertThat(dynamicProductsOutput1).isNotEqualTo(dynamicProductsOutput2);
    }
}
