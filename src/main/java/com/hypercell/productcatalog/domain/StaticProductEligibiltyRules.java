package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A StaticProductEligibiltyRules.
 */
@Entity
@Table(name = "static_product_eligibilty_rules")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaticProductEligibiltyRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "condition_id")
    private Long conditionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public StaticProductEligibiltyRules productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public StaticProductEligibiltyRules conditionId(Long conditionId) {
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
        if (!(o instanceof StaticProductEligibiltyRules)) {
            return false;
        }
        return id != null && id.equals(((StaticProductEligibiltyRules) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaticProductEligibiltyRules{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", conditionId=" + getConditionId() +
            "}";
    }
}
