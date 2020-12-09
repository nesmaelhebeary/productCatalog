package com.hypercell.productcatalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.hypercell.productcatalog.domain.enumeration.ConditionRelation;

import com.hypercell.productcatalog.domain.enumeration.ValueRelation;

/**
 * A ConditionDetails.
 */
@Entity
@Table(name = "condition_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConditionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation")
    private ConditionRelation relation;

    @Column(name = "value")
    private String value;

    @Column(name = "value_query")
    private String valueQuery;

    @Column(name = "parameter")
    private String parameter;

    @Enumerated(EnumType.STRING)
    @Column(name = "parameter_relation")
    private ValueRelation parameterRelation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConditionRelation getRelation() {
        return relation;
    }

    public ConditionDetails relation(ConditionRelation relation) {
        this.relation = relation;
        return this;
    }

    public void setRelation(ConditionRelation relation) {
        this.relation = relation;
    }

    public String getValue() {
        return value;
    }

    public ConditionDetails value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueQuery() {
        return valueQuery;
    }

    public ConditionDetails valueQuery(String valueQuery) {
        this.valueQuery = valueQuery;
        return this;
    }

    public void setValueQuery(String valueQuery) {
        this.valueQuery = valueQuery;
    }

    public String getParameter() {
        return parameter;
    }

    public ConditionDetails parameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public ValueRelation getParameterRelation() {
        return parameterRelation;
    }

    public ConditionDetails parameterRelation(ValueRelation parameterRelation) {
        this.parameterRelation = parameterRelation;
        return this;
    }

    public void setParameterRelation(ValueRelation parameterRelation) {
        this.parameterRelation = parameterRelation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionDetails)) {
            return false;
        }
        return id != null && id.equals(((ConditionDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionDetails{" +
            "id=" + getId() +
            ", relation='" + getRelation() + "'" +
            ", value='" + getValue() + "'" +
            ", valueQuery='" + getValueQuery() + "'" +
            ", parameter='" + getParameter() + "'" +
            ", parameterRelation='" + getParameterRelation() + "'" +
            "}";
    }
}
