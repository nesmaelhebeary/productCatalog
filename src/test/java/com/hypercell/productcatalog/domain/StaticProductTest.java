package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProduct.class);
        StaticProduct staticProduct1 = new StaticProduct();
        staticProduct1.setId(1L);
        StaticProduct staticProduct2 = new StaticProduct();
        staticProduct2.setId(staticProduct1.getId());
        assertThat(staticProduct1).isEqualTo(staticProduct2);
        staticProduct2.setId(2L);
        assertThat(staticProduct1).isNotEqualTo(staticProduct2);
        staticProduct1.setId(null);
        assertThat(staticProduct1).isNotEqualTo(staticProduct2);
    }
}
