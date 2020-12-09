package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.StaticProductFamilyEligibiltyRules;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticProductFamilyEligibiltyRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticProductFamilyEligibiltyRulesRepository extends JpaRepository<StaticProductFamilyEligibiltyRules, Long> {
}
