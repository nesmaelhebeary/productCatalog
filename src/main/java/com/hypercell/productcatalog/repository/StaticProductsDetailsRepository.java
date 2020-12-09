package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProductsDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProductsDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductsDetailsRepository extends JpaRepository<StaticProductsDetails, Long> {
}
