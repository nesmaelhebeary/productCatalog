package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.DynamicProductsOutput;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DynamicProductsOutput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DynamicProductsOutputRepository extends JpaRepository<DynamicProductsOutput, Long> {
}
