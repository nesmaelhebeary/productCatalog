package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.hypercell.productcatalog.domain.enumeration.StaticProductType;

/**
 * A StaticProductFamilies.
 */
@Entity
@Table(name = "static_product_families")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaticProductFamilies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private StaticProductType productType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public StaticProductFamilies name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StaticProductType getProductType() {
        return productType;
    }

    public StaticProductFamilies productType(StaticProductType productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(StaticProductType productType) {
        this.productType = productType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaticProductFamilies)) {
            return false;
        }
        return id != null && id.equals(((StaticProductFamilies) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaticProductFamilies{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", productType='" + getProductType() + "'" +
            "}";
    }
}
