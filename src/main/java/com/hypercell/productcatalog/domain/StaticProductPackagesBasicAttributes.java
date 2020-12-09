package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A StaticProductPackagesBasicAttributes.
 */
@Entity
@Table(name = "static_product_packages_basic_attributes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaticProductPackagesBasicAttributes implements Serializable {

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

    public StaticProductPackagesBasicAttributes attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Long getPackageId() {
        return packageId;
    }

    public StaticProductPackagesBasicAttributes packageId(Long packageId) {
        this.packageId = packageId;
        return this;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public StaticProductPackagesBasicAttributes attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaticProductPackagesBasicAttributes)) {
            return false;
        }
        return id != null && id.equals(((StaticProductPackagesBasicAttributes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaticProductPackagesBasicAttributes{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", packageId=" + getPackageId() +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
