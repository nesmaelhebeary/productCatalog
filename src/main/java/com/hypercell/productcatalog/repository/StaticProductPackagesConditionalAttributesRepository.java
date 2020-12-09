package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProductPackagesConditionalAttributes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProductPackagesConditionalAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductPackagesConditionalAttributesRepository extends JpaRepository<StaticProductPackagesConditionalAttributes, Long> {
}
