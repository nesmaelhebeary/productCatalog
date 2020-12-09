package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductsDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProductsDetails.class);
        StaticProductsDetails staticProductsDetails1 = new StaticProductsDetails();
        staticProductsDetails1.setId(1L);
        StaticProductsDetails staticProductsDetails2 = new StaticProductsDetails();
        staticProductsDetails2.setId(staticProductsDetails1.getId());
        assertThat(staticProductsDetails1).isEqualTo(staticProductsDetails2);
        staticProductsDetails2.setId(2L);
        assertThat(staticProductsDetails1).isNotEqualTo(staticProductsDetails2);
        staticProductsDetails1.setId(null);
        assertThat(staticProductsDetails1).isNotEqualTo(staticProductsDetails2);
    }
}
