package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProductConditionalAttributes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProductConditionalAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductConditionalAttributesRepository extends JpaRepository<StaticProductConditionalAttributes, Long> {
}
