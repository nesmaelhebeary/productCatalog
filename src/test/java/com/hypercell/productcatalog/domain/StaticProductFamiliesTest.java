package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductFamiliesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProductFamilies.class);
        StaticProductFamilies staticProductFamilies1 = new StaticProductFamilies();
        staticProductFamilies1.setId(1L);
        StaticProductFamilies staticProductFamilies2 = new StaticProductFamilies();
        staticProductFamilies2.setId(staticProductFamilies1.getId());
        assertThat(staticProductFamilies1).isEqualTo(staticProductFamilies2);
        staticProductFamilies2.setId(2L);
        assertThat(staticProductFamilies1).isNotEqualTo(staticProductFamilies2);
        staticProductFamilies1.setId(null);
        assertThat(staticProductFamilies1).isNotEqualTo(staticProductFamilies2);
    }
}
