package com.hypercell.productcatalog.repository;

import com.hypercell.productcatalog.domain.ConditionDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConditionDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConditionDetailsRepository extends JpaRepository<ConditionDetails, Long> {
}
