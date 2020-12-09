package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A StaticProductFamilyEligibiltyRules.
 */
@Entity
@Table(name = "static_product_family_eligibilty_rules")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaticProductFamilyEligibiltyRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "family_id")
    private Long familyId;

    @Column(name = "condition_id")
    private Long conditionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public StaticProductFamilyEligibiltyRules familyId(Long familyId) {
        this.familyId = familyId;
        return this;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public StaticProductFamilyEligibiltyRules conditionId(Long conditionId) {
        this.conditionId = conditionId;
        return this;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaticProductFamilyEligibiltyRules)) {
            return false;
        }
        return id != null && id.equals(((StaticProductFamilyEligibiltyRules) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaticProductFamilyEligibiltyRules{" +
            "id=" + getId() +
            ", familyId=" + getFamilyId() +
            ", conditionId=" + getConditionId() +
            "}";
    }
}
