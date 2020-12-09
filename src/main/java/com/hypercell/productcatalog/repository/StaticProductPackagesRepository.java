package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProductPackages;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProductPackages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductPackagesRepository extends JpaRepository<StaticProductPackages, Long> {
}
