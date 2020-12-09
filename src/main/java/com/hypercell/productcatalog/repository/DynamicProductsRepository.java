package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.DynamicProducts;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DynamicProducts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DynamicProductsRepository extends JpaRepository<DynamicProducts, Long> {
}
