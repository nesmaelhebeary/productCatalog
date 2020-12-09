package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.Conditions;
import com.hypercell.productcatalog.repository.ConditionsRepository;

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
 * Integration tests for the {@link ConditionsResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConditionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ConditionsRepository conditionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConditionsMockMvc;

    private Conditions conditions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conditions createEntity(EntityManager em) {
        Conditions conditions = new Conditions()
            .name(DEFAULT_NAME);
        return conditions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conditions createUpdatedEntity(EntityManager em) {
        Conditions conditions = new Conditions()
            .name(UPDATED_NAME);
        return conditions;
    }

    @BeforeEach
    public void initTest() {
        conditions = createEntity(em);
    }

    @Test
    @Transactional
    public void createConditions() throws Exception {
        int databaseSizeBeforeCreate = conditionsRepository.findAll().size();
        // Create the Conditions
        restConditionsMockMvc.perform(post("/api/conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditions)))
            .andExpect(status().isCreated());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeCreate + 1);
        Conditions testConditions = conditionsList.get(conditionsList.size() - 1);
        assertThat(testConditions.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createConditionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conditionsRepository.findAll().size();

        // Create the Conditions with an existing ID
        conditions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionsMockMvc.perform(post("/api/conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditions)))
            .andExpect(status().isBadRequest());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConditions() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList
        restConditionsMockMvc.perform(get("/api/conditions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getConditions() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get the conditions
        restConditionsMockMvc.perform(get("/api/conditions/{id}", conditions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conditions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingConditions() throws Exception {
        // Get the conditions
        restConditionsMockMvc.perform(get("/api/conditions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConditions() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();

        // Update the conditions
        Conditions updatedConditions = conditionsRepository.findById(conditions.getId()).get();
        // Disconnect from session so that the updates on updatedConditions are not directly saved in db
        em.detach(updatedConditions);
        updatedConditions
            .name(UPDATED_NAME);

        restConditionsMockMvc.perform(put("/api/conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConditions)))
            .andExpect(status().isOk());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
        Conditions testConditions = conditionsList.get(conditionsList.size() - 1);
        assertThat(testConditions.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingConditions() throws Exception {
        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionsMockMvc.perform(put("/api/conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditions)))
            .andExpect(status().isBadRequest());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConditions() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        int databaseSizeBeforeDelete = conditionsRepository.findAll().size();

        // Delete the conditions
        restConditionsMockMvc.perform(delete("/api/conditions/{id}", conditions.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
