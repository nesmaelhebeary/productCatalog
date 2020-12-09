package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProductEligibiltyRules;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProductEligibiltyRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductEligibiltyRulesRepository extends JpaRepository<StaticProductEligibiltyRules, Long> {
}
