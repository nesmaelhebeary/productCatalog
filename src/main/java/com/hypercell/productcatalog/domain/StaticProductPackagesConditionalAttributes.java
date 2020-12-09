package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A StaticProductPackagesConditionalAttributes.
 */
@Entity
@Table(name = "static_product_packages_conditional_attributes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaticProductPackagesConditionalAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "package_id")
    private Long packageId;

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

    public StaticProductPackagesConditionalAttributes attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Long getPackageId() {
        return packageId;
    }

    public StaticProductPackagesConditionalAttributes packageId(Long packageId) {
        this.packageId = packageId;
        return this;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public StaticProductPackagesConditionalAttributes attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public StaticProductPackagesConditionalAttributes conditionId(Long conditionId) {
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
        if (!(o instanceof StaticProductPackagesConditionalAttributes)) {
            return false;
        }
        return id != null && id.equals(((StaticProductPackagesConditionalAttributes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaticProductPackagesConditionalAttributes{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", packageId=" + getPackageId() +
            ", attributeValue='" + getAttributeValue() + "'" +
            ", conditionId=" + getConditionId() +
            "}";
    }
}
