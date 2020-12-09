package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.ConditionDetails;
import com.hypercell.productcatalog.repository.ConditionDetailsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.productcatalog.domain.enumeration.ConditionRelation;
import com.hypercell.productcatalog.domain.enumeration.ValueRelation;
/**
 * Integration tests for the {@link ConditionDetailsResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConditionDetailsResourceIT {

    private static final ConditionRelation DEFAULT_RELATION = ConditionRelation.OR;
    private static final ConditionRelation UPDATED_RELATION = ConditionRelation.AND;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_QUERY = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETER = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER = "BBBBBBBBBB";

    private static final ValueRelation DEFAULT_PARAMETER_RELATION = ValueRelation.IN;
    private static final ValueRelation UPDATED_PARAMETER_RELATION = ValueRelation.NOT;

    @Autowired
    private ConditionDetailsRepository conditionDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConditionDetailsMockMvc;

    private ConditionDetails conditionDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConditionDetails createEntity(EntityManager em) {
        ConditionDetails conditionDetails = new ConditionDetails()
            .relation(DEFAULT_RELATION)
            .value(DEFAULT_VALUE)
            .valueQuery(DEFAULT_VALUE_QUERY)
            .parameter(DEFAULT_PARAMETER)
            .parameterRelation(DEFAULT_PARAMETER_RELATION);
        return conditionDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConditionDetails createUpdatedEntity(EntityManager em) {
        ConditionDetails conditionDetails = new ConditionDetails()
            .relation(UPDATED_RELATION)
            .value(UPDATED_VALUE)
            .valueQuery(UPDATED_VALUE_QUERY)
            .parameter(UPDATED_PARAMETER)
            .parameterRelation(UPDATED_PARAMETER_RELATION);
        return conditionDetails;
    }

    @BeforeEach
    public void initTest() {
        conditionDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createConditionDetails() throws Exception {
        int databaseSizeBeforeCreate = conditionDetailsRepository.findAll().size();
        // Create the ConditionDetails
        restConditionDetailsMockMvc.perform(post("/api/condition-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditionDetails)))
            .andExpect(status().isCreated());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ConditionDetails testConditionDetails = conditionDetailsList.get(conditionDetailsList.size() - 1);
        assertThat(testConditionDetails.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testConditionDetails.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testConditionDetails.getValueQuery()).isEqualTo(DEFAULT_VALUE_QUERY);
        assertThat(testConditionDetails.getParameter()).isEqualTo(DEFAULT_PARAMETER);
        assertThat(testConditionDetails.getParameterRelation()).isEqualTo(DEFAULT_PARAMETER_RELATION);
    }

    @Test
    @Transactional
    public void createConditionDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conditionDetailsRepository.findAll().size();

        // Create the ConditionDetails with an existing ID
        conditionDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionDetailsMockMvc.perform(post("/api/condition-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditionDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConditionDetails() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList
        restConditionDetailsMockMvc.perform(get("/api/condition-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conditionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueQuery").value(hasItem(DEFAULT_VALUE_QUERY)))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER)))
            .andExpect(jsonPath("$.[*].parameterRelation").value(hasItem(DEFAULT_PARAMETER_RELATION.toString())));
    }
    
    @Test
    @Transactional
    public void getConditionDetails() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get the conditionDetails
        restConditionDetailsMockMvc.perform(get("/api/condition-details/{id}", conditionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conditionDetails.getId().intValue()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueQuery").value(DEFAULT_VALUE_QUERY))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER))
            .andExpect(jsonPath("$.parameterRelation").value(DEFAULT_PARAMETER_RELATION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingConditionDetails() throws Exception {
        // Get the conditionDetails
        restConditionDetailsMockMvc.perform(get("/api/condition-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConditionDetails() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();

        // Update the conditionDetails
        ConditionDetails updatedConditionDetails = conditionDetailsRepository.findById(conditionDetails.getId()).get();
        // Disconnect from session so that the updates on updatedConditionDetails are not directly saved in db
        em.detach(updatedConditionDetails);
        updatedConditionDetails
            .relation(UPDATED_RELATION)
            .value(UPDATED_VALUE)
            .valueQuery(UPDATED_VALUE_QUERY)
            .parameter(UPDATED_PARAMETER)
            .parameterRelation(UPDATED_PARAMETER_RELATION);

        restConditionDetailsMockMvc.perform(put("/api/condition-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConditionDetails)))
            .andExpect(status().isOk());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
        ConditionDetails testConditionDetails = conditionDetailsList.get(conditionDetailsList.size() - 1);
        assertThat(testConditionDetails.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testConditionDetails.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testConditionDetails.getValueQuery()).isEqualTo(UPDATED_VALUE_QUERY);
        assertThat(testConditionDetails.getParameter()).isEqualTo(UPDATED_PARAMETER);
        assertThat(testConditionDetails.getParameterRelation()).isEqualTo(UPDATED_PARAMETER_RELATION);
    }

    @Test
    @Transactional
    public void updateNonExistingConditionDetails() throws Exception {
        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionDetailsMockMvc.perform(put("/api/condition-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditionDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConditionDetails() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        int databaseSizeBeforeDelete = conditionDetailsRepository.findAll().size();

        // Delete the conditionDetails
        restConditionDetailsMockMvc.perform(delete("/api/condition-details/{id}", conditionDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
