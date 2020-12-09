package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.ExternalSystems;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExternalSystems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternalSystemsRepository extends JpaRepository<ExternalSystems, Long> {
}
