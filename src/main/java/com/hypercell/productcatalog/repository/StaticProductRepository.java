package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProduct;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductRepository extends JpaRepository<StaticProduct, Long> {
}
