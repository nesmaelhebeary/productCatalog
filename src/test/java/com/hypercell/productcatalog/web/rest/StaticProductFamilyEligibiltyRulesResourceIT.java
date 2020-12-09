package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProductFamilyEligibiltyRules;
import com.hypercell.productcatalog.repository.StaticProductFamilyEligibiltyRulesRepository;

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
 * Integration tests for the {@link StaticProductFamilyEligibiltyRulesResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductFamilyEligibiltyRulesResourceIT {

    private static final Long DEFAULT_FAMILY_ID = 1L;
    private static final Long UPDATED_FAMILY_ID = 2L;

    private static final Long DEFAULT_CONDITION_ID = 1L;
    private static final Long UPDATED_CONDITION_ID = 2L;

    @Autowired
    private StaticProductFamilyEligibiltyRulesRepository staticProductFamilyEligibiltyRulesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductFamilyEligibiltyRulesMockMvc;

    private StaticProductFamilyEligibiltyRules staticProductFamilyEligibiltyRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductFamilyEligibiltyRules createEntity(EntityManager em) {
        StaticProductFamilyEligibiltyRules staticProductFamilyEligibiltyRules = new StaticProductFamilyEligibiltyRules()
            .familyId(DEFAULT_FAMILY_ID)
            .conditionId(DEFAULT_CONDITION_ID);
        return staticProductFamilyEligibiltyRules;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductFamilyEligibiltyRules createUpdatedEntity(EntityManager em) {
        StaticProductFamilyEligibiltyRules staticProductFamilyEligibiltyRules = new StaticProductFamilyEligibiltyRules()
            .familyId(UPDATED_FAMILY_ID)
            .conditionId(UPDATED_CONDITION_ID);
        return staticProductFamilyEligibiltyRules;
    }

    @BeforeEach
    public void initTest() {
        staticProductFamilyEligibiltyRules = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProductFamilyEligibiltyRules() throws Exception {
        int databaseSizeBeforeCreate = staticProductFamilyEligibiltyRulesRepository.findAll().size();
        // Create the StaticProductFamilyEligibiltyRules
        restStaticProductFamilyEligibiltyRulesMockMvc.perform(post("/api/static-product-family-eligibilty-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductFamilyEligibiltyRules)))
            .andExpect(status().isCreated());

        // Validate the StaticProductFamilyEligibiltyRules in the database
        List<StaticProductFamilyEligibiltyRules> staticProductFamilyEligibiltyRulesList = staticProductFamilyEligibiltyRulesRepository.findAll();
        assertThat(staticProductFamilyEligibiltyRulesList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProductFamilyEligibiltyRules testStaticProductFamilyEligibiltyRules = staticProductFamilyEligibiltyRulesList.get(staticProductFamilyEligibiltyRulesList.size() - 1);
        assertThat(testStaticProductFamilyEligibiltyRules.getFamilyId()).isEqualTo(DEFAULT_FAMILY_ID);
        assertThat(testStaticProductFamilyEligibiltyRules.getConditionId()).isEqualTo(DEFAULT_CONDITION_ID);
    }

    @Test
    @Transactional
    public void createStaticProductFamilyEligibiltyRulesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductFamilyEligibiltyRulesRepository.findAll().size();

        // Create the StaticProductFamilyEligibiltyRules with an existing ID
        staticProductFamilyEligibiltyRules.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductFamilyEligibiltyRulesMockMvc.perform(post("/api/static-product-family-eligibilty-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductFamilyEligibiltyRules)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductFamilyEligibiltyRules in the database
        List<StaticProductFamilyEligibiltyRules> staticProductFamilyEligibiltyRulesList = staticProductFamilyEligibiltyRulesRepository.findAll();
        assertThat(staticProductFamilyEligibiltyRulesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProductFamilyEligibiltyRules() throws Exception {
        // Initialize the database
        staticProductFamilyEligibiltyRulesRepository.saveAndFlush(staticProductFamilyEligibiltyRules);

        // Get all the staticProductFamilyEligibiltyRulesList
        restStaticProductFamilyEligibiltyRulesMockMvc.perform(get("/api/static-product-family-eligibilty-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProductFamilyEligibiltyRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].familyId").value(hasItem(DEFAULT_FAMILY_ID.intValue())))
            .andExpect(jsonPath("$.[*].conditionId").value(hasItem(DEFAULT_CONDITION_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getStaticProductFamilyEligibiltyRules() throws Exception {
        // Initialize the database
        staticProductFamilyEligibiltyRulesRepository.saveAndFlush(staticProductFamilyEligibiltyRules);

        // Get the staticProductFamilyEligibiltyRules
        restStaticProductFamilyEligibiltyRulesMockMvc.perform(get("/api/static-product-family-eligibilty-rules/{id}", staticProductFamilyEligibiltyRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProductFamilyEligibiltyRules.getId().intValue()))
            .andExpect(jsonPath("$.familyId").value(DEFAULT_FAMILY_ID.intValue()))
            .andExpect(jsonPath("$.conditionId").value(DEFAULT_CONDITION_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProductFamilyEligibiltyRules() throws Exception {
        // Get the staticProductFamilyEligibiltyRules
        restStaticProductFamilyEligibiltyRulesMockMvc.perform(get("/api/static-product-family-eligibilty-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProductFamilyEligibiltyRules() throws Exception {
        // Initialize the database
        staticProductFamilyEligibiltyRulesRepository.saveAndFlush(staticProductFamilyEligibiltyRules);

        int databaseSizeBeforeUpdate = staticProductFamilyEligibiltyRulesRepository.findAll().size();

        // Update the staticProductFamilyEligibiltyRules
        StaticProductFamilyEligibiltyRules updatedStaticProductFamilyEligibiltyRules = staticProductFamilyEligibiltyRulesRepository.findById(staticProductFamilyEligibiltyRules.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProductFamilyEligibiltyRules are not directly saved in db
        em.detach(updatedStaticProductFamilyEligibiltyRules);
        updatedStaticProductFamilyEligibiltyRules
            .familyId(UPDATED_FAMILY_ID)
            .conditionId(UPDATED_CONDITION_ID);

        restStaticProductFamilyEligibiltyRulesMockMvc.perform(put("/api/static-product-family-eligibilty-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProductFamilyEligibiltyRules)))
            .andExpect(status().isOk());

        // Validate the StaticProductFamilyEligibiltyRules in the database
        List<StaticProductFamilyEligibiltyRules> staticProductFamilyEligibiltyRulesList = staticProductFamilyEligibiltyRulesRepository.findAll();
        assertThat(staticProductFamilyEligibiltyRulesList).hasSize(databaseSizeBeforeUpdate);
        StaticProductFamilyEligibiltyRules testStaticProductFamilyEligibiltyRules = staticProductFamilyEligibiltyRulesList.get(staticProductFamilyEligibiltyRulesList.size() - 1);
        assertThat(testStaticProductFamilyEligibiltyRules.getFamilyId()).isEqualTo(UPDATED_FAMILY_ID);
        assertThat(testStaticProductFamilyEligibiltyRules.getConditionId()).isEqualTo(UPDATED_CONDITION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProductFamilyEligibiltyRules() throws Exception {
        int databaseSizeBeforeUpdate = staticProductFamilyEligibiltyRulesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductFamilyEligibiltyRulesMockMvc.perform(put("/api/static-product-family-eligibilty-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductFamilyEligibiltyRules)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductFamilyEligibiltyRules in the database
        List<StaticProductFamilyEligibiltyRules> staticProductFamilyEligibiltyRulesList = staticProductFamilyEligibiltyRulesRepository.findAll();
        assertThat(staticProductFamilyEligibiltyRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProductFamilyEligibiltyRules() throws Exception {
        // Initialize the database
        staticProductFamilyEligibiltyRulesRepository.saveAndFlush(staticProductFamilyEligibiltyRules);

        int databaseSizeBeforeDelete = staticProductFamilyEligibiltyRulesRepository.findAll().size();

        // Delete the staticProductFamilyEligibiltyRules
        restStaticProductFamilyEligibiltyRulesMockMvc.perform(delete("/api/static-product-family-eligibilty-rules/{id}", staticProductFamilyEligibiltyRules.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProductFamilyEligibiltyRules> staticProductFamilyEligibiltyRulesList = staticProductFamilyEligibiltyRulesRepository.findAll();
        assertThat(staticProductFamilyEligibiltyRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
