package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProductFamilies;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProductFamilies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductFamiliesRepository extends JpaRepository<StaticProductFamilies, Long> {
}
