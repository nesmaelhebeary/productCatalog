package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.hypercell.productcatalog.domain.enumeration.ResourceType;

/**
 * A StaticProductsDetails.
 */
@Entity
@Table(name = "static_products_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaticProductsDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "resource_value")
    private String resourceValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

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

    public StaticProductsDetails productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public StaticProductsDetails resourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceValue() {
        return resourceValue;
    }

    public StaticProductsDetails resourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
        return this;
    }

    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public StaticProductsDetails resourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaticProductsDetails)) {
            return false;
        }
        return id != null && id.equals(((StaticProductsDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaticProductsDetails{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", resourceId='" + getResourceId() + "'" +
            ", resourceValue='" + getResourceValue() + "'" +
            ", resourceType='" + getResourceType() + "'" +
            "}";
    }
}
