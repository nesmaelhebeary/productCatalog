package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class DynamicProductsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DynamicProducts.class);
        DynamicProducts dynamicProducts1 = new DynamicProducts();
        dynamicProducts1.setId(1L);
        DynamicProducts dynamicProducts2 = new DynamicProducts();
        dynamicProducts2.setId(dynamicProducts1.getId());
        assertThat(dynamicProducts1).isEqualTo(dynamicProducts2);
        dynamicProducts2.setId(2L);
        assertThat(dynamicProducts1).isNotEqualTo(dynamicProducts2);
        dynamicProducts1.setId(null);
        assertThat(dynamicProducts1).isNotEqualTo(dynamicProducts2);
    }
}
