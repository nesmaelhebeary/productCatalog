package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProductEligibiltyRules;
import com.hypercell.productcatalog.repository.StaticProductEligibiltyRulesRepository;

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
 * Integration tests for the {@link StaticProductEligibiltyRulesResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductEligibiltyRulesResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Long DEFAULT_CONDITION_ID = 1L;
    private static final Long UPDATED_CONDITION_ID = 2L;

    @Autowired
    private StaticProductEligibiltyRulesRepository staticProductEligibiltyRulesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductEligibiltyRulesMockMvc;

    private StaticProductEligibiltyRules staticProductEligibiltyRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductEligibiltyRules createEntity(EntityManager em) {
        StaticProductEligibiltyRules staticProductEligibiltyRules = new StaticProductEligibiltyRules()
            .productId(DEFAULT_PRODUCT_ID)
            .conditionId(DEFAULT_CONDITION_ID);
        return staticProductEligibiltyRules;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductEligibiltyRules createUpdatedEntity(EntityManager em) {
        StaticProductEligibiltyRules staticProductEligibiltyRules = new StaticProductEligibiltyRules()
            .productId(UPDATED_PRODUCT_ID)
            .conditionId(UPDATED_CONDITION_ID);
        return staticProductEligibiltyRules;
    }

    @BeforeEach
    public void initTest() {
        staticProductEligibiltyRules = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProductEligibiltyRules() throws Exception {
        int databaseSizeBeforeCreate = staticProductEligibiltyRulesRepository.findAll().size();
        // Create the StaticProductEligibiltyRules
        restStaticProductEligibiltyRulesMockMvc.perform(post("/api/static-product-eligibilty-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductEligibiltyRules)))
            .andExpect(status().isCreated());

        // Validate the StaticProductEligibiltyRules in the database
        List<StaticProductEligibiltyRules> staticProductEligibiltyRulesList = staticProductEligibiltyRulesRepository.findAll();
        assertThat(staticProductEligibiltyRulesList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProductEligibiltyRules testStaticProductEligibiltyRules = staticProductEligibiltyRulesList.get(staticProductEligibiltyRulesList.size() - 1);
        assertThat(testStaticProductEligibiltyRules.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testStaticProductEligibiltyRules.getConditionId()).isEqualTo(DEFAULT_CONDITION_ID);
    }

    @Test
    @Transactional
    public void createStaticProductEligibiltyRulesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductEligibiltyRulesRepository.findAll().size();

        // Create the StaticProductEligibiltyRules with an existing ID
        staticProductEligibiltyRules.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductEligibiltyRulesMockMvc.perform(post("/api/static-product-eligibilty-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductEligibiltyRules)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductEligibiltyRules in the database
        List<StaticProductEligibiltyRules> staticProductEligibiltyRulesList = staticProductEligibiltyRulesRepository.findAll();
        assertThat(staticProductEligibiltyRulesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProductEligibiltyRules() throws Exception {
        // Initialize the database
        staticProductEligibiltyRulesRepository.saveAndFlush(staticProductEligibiltyRules);

        // Get all the staticProductEligibiltyRulesList
        restStaticProductEligibiltyRulesMockMvc.perform(get("/api/static-product-eligibilty-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProductEligibiltyRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].conditionId").value(hasItem(DEFAULT_CONDITION_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getStaticProductEligibiltyRules() throws Exception {
        // Initialize the database
        staticProductEligibiltyRulesRepository.saveAndFlush(staticProductEligibiltyRules);

        // Get the staticProductEligibiltyRules
        restStaticProductEligibiltyRulesMockMvc.perform(get("/api/static-product-eligibilty-rules/{id}", staticProductEligibiltyRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProductEligibiltyRules.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.conditionId").value(DEFAULT_CONDITION_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProductEligibiltyRules() throws Exception {
        // Get the staticProductEligibiltyRules
        restStaticProductEligibiltyRulesMockMvc.perform(get("/api/static-product-eligibilty-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProductEligibiltyRules() throws Exception {
        // Initialize the database
        staticProductEligibiltyRulesRepository.saveAndFlush(staticProductEligibiltyRules);

        int databaseSizeBeforeUpdate = staticProductEligibiltyRulesRepository.findAll().size();

        // Update the staticProductEligibiltyRules
        StaticProductEligibiltyRules updatedStaticProductEligibiltyRules = staticProductEligibiltyRulesRepository.findById(staticProductEligibiltyRules.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProductEligibiltyRules are not directly saved in db
        em.detach(updatedStaticProductEligibiltyRules);
        updatedStaticProductEligibiltyRules
            .productId(UPDATED_PRODUCT_ID)
            .conditionId(UPDATED_CONDITION_ID);

        restStaticProductEligibiltyRulesMockMvc.perform(put("/api/static-product-eligibilty-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProductEligibiltyRules)))
            .andExpect(status().isOk());

        // Validate the StaticProductEligibiltyRules in the database
        List<StaticProductEligibiltyRules> staticProductEligibiltyRulesList = staticProductEligibiltyRulesRepository.findAll();
        assertThat(staticProductEligibiltyRulesList).hasSize(databaseSizeBeforeUpdate);
        StaticProductEligibiltyRules testStaticProductEligibiltyRules = staticProductEligibiltyRulesList.get(staticProductEligibiltyRulesList.size() - 1);
        assertThat(testStaticProductEligibiltyRules.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testStaticProductEligibiltyRules.getConditionId()).isEqualTo(UPDATED_CONDITION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProductEligibiltyRules() throws Exception {
        int databaseSizeBeforeUpdate = staticProductEligibiltyRulesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductEligibiltyRulesMockMvc.perform(put("/api/static-product-eligibilty-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductEligibiltyRules)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductEligibiltyRules in the database
        List<StaticProductEligibiltyRules> staticProductEligibiltyRulesList = staticProductEligibiltyRulesRepository.findAll();
        assertThat(staticProductEligibiltyRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProductEligibiltyRules() throws Exception {
        // Initialize the database
        staticProductEligibiltyRulesRepository.saveAndFlush(staticProductEligibiltyRules);

        int databaseSizeBeforeDelete = staticProductEligibiltyRulesRepository.findAll().size();

        // Delete the staticProductEligibiltyRules
        restStaticProductEligibiltyRulesMockMvc.perform(delete("/api/static-product-eligibilty-rules/{id}", staticProductEligibiltyRules.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProductEligibiltyRules> staticProductEligibiltyRulesList = staticProductEligibiltyRulesRepository.findAll();
        assertThat(staticProductEligibiltyRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
