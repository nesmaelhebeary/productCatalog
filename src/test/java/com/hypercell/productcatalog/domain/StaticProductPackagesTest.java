package com.hypercell.productcatalog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hypercell.productcatalog.web.rest.TestUtil;

public class StaticProductPackagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticProductPackages.class);
        StaticProductPackages staticProductPackages1 = new StaticProductPackages();
        staticProductPackages1.setId(1L);
        StaticProductPackages staticProductPackages2 = new StaticProductPackages();
        staticProductPackages2.setId(staticProductPackages1.getId());
        assertThat(staticProductPackages1).isEqualTo(staticProductPackages2);
        staticProductPackages2.setId(2L);
        assertThat(staticProductPackages1).isNotEqualTo(staticProductPackages2);
        staticProductPackages1.setId(null);
        assertThat(staticProductPackages1).isNotEqualTo(staticProductPackages2);
    }
}
