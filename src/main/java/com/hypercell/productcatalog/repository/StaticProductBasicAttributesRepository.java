package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProductBasicAttributes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProductBasicAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductBasicAttributesRepository extends JpaRepository<StaticProductBasicAttributes, Long> {
}
