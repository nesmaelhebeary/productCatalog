package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.DynamicProductsOutput;
import com.hypercell.productcatalog.repository.DynamicProductsOutputRepository;

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

/**
 * Integration tests for the {@link DynamicProductsOutputResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DynamicProductsOutputResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_EXPRESSION = "BBBBBBBBBB";

    @Autowired
    private DynamicProductsOutputRepository dynamicProductsOutputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDynamicProductsOutputMockMvc;

    private DynamicProductsOutput dynamicProductsOutput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicProductsOutput createEntity(EntityManager em) {
        DynamicProductsOutput dynamicProductsOutput = new DynamicProductsOutput()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldSource(DEFAULT_FIELD_SOURCE)
            .fieldExpression(DEFAULT_FIELD_EXPRESSION);
        return dynamicProductsOutput;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicProductsOutput createUpdatedEntity(EntityManager em) {
        DynamicProductsOutput dynamicProductsOutput = new DynamicProductsOutput()
            .fieldName(UPDATED_FIELD_NAME)
            .fieldSource(UPDATED_FIELD_SOURCE)
            .fieldExpression(UPDATED_FIELD_EXPRESSION);
        return dynamicProductsOutput;
    }

    @BeforeEach
    public void initTest() {
        dynamicProductsOutput = createEntity(em);
    }

    @Test
    @Transactional
    public void createDynamicProductsOutput() throws Exception {
        int databaseSizeBeforeCreate = dynamicProductsOutputRepository.findAll().size();
        // Create the DynamicProductsOutput
        restDynamicProductsOutputMockMvc.perform(post("/api/dynamic-products-outputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicProductsOutput)))
            .andExpect(status().isCreated());

        // Validate the DynamicProductsOutput in the database
        List<DynamicProductsOutput> dynamicProductsOutputList = dynamicProductsOutputRepository.findAll();
        assertThat(dynamicProductsOutputList).hasSize(databaseSizeBeforeCreate + 1);
        DynamicProductsOutput testDynamicProductsOutput = dynamicProductsOutputList.get(dynamicProductsOutputList.size() - 1);
        assertThat(testDynamicProductsOutput.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testDynamicProductsOutput.getFieldSource()).isEqualTo(DEFAULT_FIELD_SOURCE);
        assertThat(testDynamicProductsOutput.getFieldExpression()).isEqualTo(DEFAULT_FIELD_EXPRESSION);
    }

    @Test
    @Transactional
    public void createDynamicProductsOutputWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dynamicProductsOutputRepository.findAll().size();

        // Create the DynamicProductsOutput with an existing ID
        dynamicProductsOutput.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDynamicProductsOutputMockMvc.perform(post("/api/dynamic-products-outputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicProductsOutput)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicProductsOutput in the database
        List<DynamicProductsOutput> dynamicProductsOutputList = dynamicProductsOutputRepository.findAll();
        assertThat(dynamicProductsOutputList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDynamicProductsOutputs() throws Exception {
        // Initialize the database
        dynamicProductsOutputRepository.saveAndFlush(dynamicProductsOutput);

        // Get all the dynamicProductsOutputList
        restDynamicProductsOutputMockMvc.perform(get("/api/dynamic-products-outputs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dynamicProductsOutput.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldSource").value(hasItem(DEFAULT_FIELD_SOURCE)))
            .andExpect(jsonPath("$.[*].fieldExpression").value(hasItem(DEFAULT_FIELD_EXPRESSION)));
    }
    
    @Test
    @Transactional
    public void getDynamicProductsOutput() throws Exception {
        // Initialize the database
        dynamicProductsOutputRepository.saveAndFlush(dynamicProductsOutput);

        // Get the dynamicProductsOutput
        restDynamicProductsOutputMockMvc.perform(get("/api/dynamic-products-outputs/{id}", dynamicProductsOutput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dynamicProductsOutput.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.fieldSource").value(DEFAULT_FIELD_SOURCE))
            .andExpect(jsonPath("$.fieldExpression").value(DEFAULT_FIELD_EXPRESSION));
    }
    @Test
    @Transactional
    public void getNonExistingDynamicProductsOutput() throws Exception {
        // Get the dynamicProductsOutput
        restDynamicProductsOutputMockMvc.perform(get("/api/dynamic-products-outputs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDynamicProductsOutput() throws Exception {
        // Initialize the database
        dynamicProductsOutputRepository.saveAndFlush(dynamicProductsOutput);

        int databaseSizeBeforeUpdate = dynamicProductsOutputRepository.findAll().size();

        // Update the dynamicProductsOutput
        DynamicProductsOutput updatedDynamicProductsOutput = dynamicProductsOutputRepository.findById(dynamicProductsOutput.getId()).get();
        // Disconnect from session so that the updates on updatedDynamicProductsOutput are not directly saved in db
        em.detach(updatedDynamicProductsOutput);
        updatedDynamicProductsOutput
            .fieldName(UPDATED_FIELD_NAME)
            .fieldSource(UPDATED_FIELD_SOURCE)
            .fieldExpression(UPDATED_FIELD_EXPRESSION);

        restDynamicProductsOutputMockMvc.perform(put("/api/dynamic-products-outputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDynamicProductsOutput)))
            .andExpect(status().isOk());

        // Validate the DynamicProductsOutput in the database
        List<DynamicProductsOutput> dynamicProductsOutputList = dynamicProductsOutputRepository.findAll();
        assertThat(dynamicProductsOutputList).hasSize(databaseSizeBeforeUpdate);
        DynamicProductsOutput testDynamicProductsOutput = dynamicProductsOutputList.get(dynamicProductsOutputList.size() - 1);
        assertThat(testDynamicProductsOutput.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testDynamicProductsOutput.getFieldSource()).isEqualTo(UPDATED_FIELD_SOURCE);
        assertThat(testDynamicProductsOutput.getFieldExpression()).isEqualTo(UPDATED_FIELD_EXPRESSION);
    }

    @Test
    @Transactional
    public void updateNonExistingDynamicProductsOutput() throws Exception {
        int databaseSizeBeforeUpdate = dynamicProductsOutputRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDynamicProductsOutputMockMvc.perform(put("/api/dynamic-products-outputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicProductsOutput)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicProductsOutput in the database
        List<DynamicProductsOutput> dynamicProductsOutputList = dynamicProductsOutputRepository.findAll();
        assertThat(dynamicProductsOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDynamicProductsOutput() throws Exception {
        // Initialize the database
        dynamicProductsOutputRepository.saveAndFlush(dynamicProductsOutput);

        int databaseSizeBeforeDelete = dynamicProductsOutputRepository.findAll().size();

        // Delete the dynamicProductsOutput
        restDynamicProductsOutputMockMvc.perform(delete("/api/dynamic-products-outputs/{id}", dynamicProductsOutput.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DynamicProductsOutput> dynamicProductsOutputList = dynamicProductsOutputRepository.findAll();
        assertThat(dynamicProductsOutputList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
