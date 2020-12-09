package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A StaticProductPackages.
 */
@Entity
@Table(name = "static_product_packages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaticProductPackages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "fees")
    private Double fees;

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

    public StaticProductPackages name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductId() {
        return productId;
    }

    public StaticProductPackages productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getFees() {
        return fees;
    }

    public StaticProductPackages fees(Double fees) {
        this.fees = fees;
        return this;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaticProductPackages)) {
            return false;
        }
        return id != null && id.equals(((StaticProductPackages) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaticProductPackages{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", productId=" + getProductId() +
            ", fees=" + getFees() +
            "}";
    }
}
