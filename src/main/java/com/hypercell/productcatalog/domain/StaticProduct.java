package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A StaticProduct.
 */
@Entity
@Table(name = "static_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaticProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "family_id")
    private Long familyId;

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

    public StaticProduct name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public StaticProduct familyId(Long familyId) {
        this.familyId = familyId;
        return this;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaticProduct)) {
            return false;
        }
        return id != null && id.equals(((StaticProduct) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaticProduct{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", familyId=" + getFamilyId() +
            "}";
    }
}
