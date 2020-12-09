package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A StaticProductConditionalAttributes.
 */
@Entity
@Table(name = "static_product_conditional_attributes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaticProductConditionalAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "attribute_value")
    private String attributeValue;

    @Column(name = "condition_id")
    private Long conditionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public StaticProductConditionalAttributes attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Long getProductId() {
        return productId;
    }

    public StaticProductConditionalAttributes productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public StaticProductConditionalAttributes attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public StaticProductConditionalAttributes conditionId(Long conditionId) {
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
        if (!(o instanceof StaticProductConditionalAttributes)) {
            return false;
        }
        return id != null && id.equals(((StaticProductConditionalAttributes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaticProductConditionalAttributes{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", productId=" + getProductId() +
            ", attributeValue='" + getAttributeValue() + "'" +
            ", conditionId=" + getConditionId() +
            "}";
    }
}
