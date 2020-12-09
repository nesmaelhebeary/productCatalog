package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DynamicProductsOutput.
 */
@Entity
@Table(name = "dynamic_products_output")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DynamicProductsOutput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_source")
    private String fieldSource;

    @Column(name = "field_expression")
    private String fieldExpression;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public DynamicProductsOutput fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldSource() {
        return fieldSource;
    }

    public DynamicProductsOutput fieldSource(String fieldSource) {
        this.fieldSource = fieldSource;
        return this;
    }

    public void setFieldSource(String fieldSource) {
        this.fieldSource = fieldSource;
    }

    public String getFieldExpression() {
        return fieldExpression;
    }

    public DynamicProductsOutput fieldExpression(String fieldExpression) {
        this.fieldExpression = fieldExpression;
        return this;
    }

    public void setFieldExpression(String fieldExpression) {
        this.fieldExpression = fieldExpression;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DynamicProductsOutput)) {
            return false;
        }
        return id != null && id.equals(((DynamicProductsOutput) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DynamicProductsOutput{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldSource='" + getFieldSource() + "'" +
            ", fieldExpression='" + getFieldExpression() + "'" +
            "}";
    }
}
