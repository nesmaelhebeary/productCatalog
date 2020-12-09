package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.Conditions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Conditions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConditionsRepository extends JpaRepository<Conditions, Long> {
}
