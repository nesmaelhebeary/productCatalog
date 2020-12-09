package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProductPackagesBasicAttributes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProductPackagesBasicAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductPackagesBasicAttributesRepository extends JpaRepository<StaticProductPackagesBasicAttributes, Long> {
}
