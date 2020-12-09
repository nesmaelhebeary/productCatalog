package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProductPackagesConditionalAttributes;
import com.hypercell.productcatalog.repository.StaticProductPackagesConditionalAttributesRepository;

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
 * Integration tests for the {@link StaticProductPackagesConditionalAttributesResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductPackagesConditionalAttributesResourceIT {

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PACKAGE_ID = 1L;
    private static final Long UPDATED_PACKAGE_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_CONDITION_ID = 1L;
    private static final Long UPDATED_CONDITION_ID = 2L;

    @Autowired
    private StaticProductPackagesConditionalAttributesRepository staticProductPackagesConditionalAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductPackagesConditionalAttributesMockMvc;

    private StaticProductPackagesConditionalAttributes staticProductPackagesConditionalAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductPackagesConditionalAttributes createEntity(EntityManager em) {
        StaticProductPackagesConditionalAttributes staticProductPackagesConditionalAttributes = new StaticProductPackagesConditionalAttributes()
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .packageId(DEFAULT_PACKAGE_ID)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE)
            .conditionId(DEFAULT_CONDITION_ID);
        return staticProductPackagesConditionalAttributes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductPackagesConditionalAttributes createUpdatedEntity(EntityManager em) {
        StaticProductPackagesConditionalAttributes staticProductPackagesConditionalAttributes = new StaticProductPackagesConditionalAttributes()
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .packageId(UPDATED_PACKAGE_ID)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .conditionId(UPDATED_CONDITION_ID);
        return staticProductPackagesConditionalAttributes;
    }

    @BeforeEach
    public void initTest() {
        staticProductPackagesConditionalAttributes = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProductPackagesConditionalAttributes() throws Exception {
        int databaseSizeBeforeCreate = staticProductPackagesConditionalAttributesRepository.findAll().size();
        // Create the StaticProductPackagesConditionalAttributes
        restStaticProductPackagesConditionalAttributesMockMvc.perform(post("/api/static-product-packages-conditional-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductPackagesConditionalAttributes)))
            .andExpect(status().isCreated());

        // Validate the StaticProductPackagesConditionalAttributes in the database
        List<StaticProductPackagesConditionalAttributes> staticProductPackagesConditionalAttributesList = staticProductPackagesConditionalAttributesRepository.findAll();
        assertThat(staticProductPackagesConditionalAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProductPackagesConditionalAttributes testStaticProductPackagesConditionalAttributes = staticProductPackagesConditionalAttributesList.get(staticProductPackagesConditionalAttributesList.size() - 1);
        assertThat(testStaticProductPackagesConditionalAttributes.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testStaticProductPackagesConditionalAttributes.getPackageId()).isEqualTo(DEFAULT_PACKAGE_ID);
        assertThat(testStaticProductPackagesConditionalAttributes.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
        assertThat(testStaticProductPackagesConditionalAttributes.getConditionId()).isEqualTo(DEFAULT_CONDITION_ID);
    }

    @Test
    @Transactional
    public void createStaticProductPackagesConditionalAttributesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductPackagesConditionalAttributesRepository.findAll().size();

        // Create the StaticProductPackagesConditionalAttributes with an existing ID
        staticProductPackagesConditionalAttributes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductPackagesConditionalAttributesMockMvc.perform(post("/api/static-product-packages-conditional-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductPackagesConditionalAttributes)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductPackagesConditionalAttributes in the database
        List<StaticProductPackagesConditionalAttributes> staticProductPackagesConditionalAttributesList = staticProductPackagesConditionalAttributesRepository.findAll();
        assertThat(staticProductPackagesConditionalAttributesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProductPackagesConditionalAttributes() throws Exception {
        // Initialize the database
        staticProductPackagesConditionalAttributesRepository.saveAndFlush(staticProductPackagesConditionalAttributes);

        // Get all the staticProductPackagesConditionalAttributesList
        restStaticProductPackagesConditionalAttributesMockMvc.perform(get("/api/static-product-packages-conditional-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProductPackagesConditionalAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].packageId").value(hasItem(DEFAULT_PACKAGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)))
            .andExpect(jsonPath("$.[*].conditionId").value(hasItem(DEFAULT_CONDITION_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getStaticProductPackagesConditionalAttributes() throws Exception {
        // Initialize the database
        staticProductPackagesConditionalAttributesRepository.saveAndFlush(staticProductPackagesConditionalAttributes);

        // Get the staticProductPackagesConditionalAttributes
        restStaticProductPackagesConditionalAttributesMockMvc.perform(get("/api/static-product-packages-conditional-attributes/{id}", staticProductPackagesConditionalAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProductPackagesConditionalAttributes.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.packageId").value(DEFAULT_PACKAGE_ID.intValue()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE))
            .andExpect(jsonPath("$.conditionId").value(DEFAULT_CONDITION_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProductPackagesConditionalAttributes() throws Exception {
        // Get the staticProductPackagesConditionalAttributes
        restStaticProductPackagesConditionalAttributesMockMvc.perform(get("/api/static-product-packages-conditional-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProductPackagesConditionalAttributes() throws Exception {
        // Initialize the database
        staticProductPackagesConditionalAttributesRepository.saveAndFlush(staticProductPackagesConditionalAttributes);

        int databaseSizeBeforeUpdate = staticProductPackagesConditionalAttributesRepository.findAll().size();

        // Update the staticProductPackagesConditionalAttributes
        StaticProductPackagesConditionalAttributes updatedStaticProductPackagesConditionalAttributes = staticProductPackagesConditionalAttributesRepository.findById(staticProductPackagesConditionalAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProductPackagesConditionalAttributes are not directly saved in db
        em.detach(updatedStaticProductPackagesConditionalAttributes);
        updatedStaticProductPackagesConditionalAttributes
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .packageId(UPDATED_PACKAGE_ID)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .conditionId(UPDATED_CONDITION_ID);

        restStaticProductPackagesConditionalAttributesMockMvc.perform(put("/api/static-product-packages-conditional-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProductPackagesConditionalAttributes)))
            .andExpect(status().isOk());

        // Validate the StaticProductPackagesConditionalAttributes in the database
        List<StaticProductPackagesConditionalAttributes> staticProductPackagesConditionalAttributesList = staticProductPackagesConditionalAttributesRepository.findAll();
        assertThat(staticProductPackagesConditionalAttributesList).hasSize(databaseSizeBeforeUpdate);
        StaticProductPackagesConditionalAttributes testStaticProductPackagesConditionalAttributes = staticProductPackagesConditionalAttributesList.get(staticProductPackagesConditionalAttributesList.size() - 1);
        assertThat(testStaticProductPackagesConditionalAttributes.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testStaticProductPackagesConditionalAttributes.getPackageId()).isEqualTo(UPDATED_PACKAGE_ID);
        assertThat(testStaticProductPackagesConditionalAttributes.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
        assertThat(testStaticProductPackagesConditionalAttributes.getConditionId()).isEqualTo(UPDATED_CONDITION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProductPackagesConditionalAttributes() throws Exception {
        int databaseSizeBeforeUpdate = staticProductPackagesConditionalAttributesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductPackagesConditionalAttributesMockMvc.perform(put("/api/static-product-packages-conditional-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductPackagesConditionalAttributes)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductPackagesConditionalAttributes in the database
        List<StaticProductPackagesConditionalAttributes> staticProductPackagesConditionalAttributesList = staticProductPackagesConditionalAttributesRepository.findAll();
        assertThat(staticProductPackagesConditionalAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProductPackagesConditionalAttributes() throws Exception {
        // Initialize the database
        staticProductPackagesConditionalAttributesRepository.saveAndFlush(staticProductPackagesConditionalAttributes);

        int databaseSizeBeforeDelete = staticProductPackagesConditionalAttributesRepository.findAll().size();

        // Delete the staticProductPackagesConditionalAttributes
        restStaticProductPackagesConditionalAttributesMockMvc.perform(delete("/api/static-product-packages-conditional-attributes/{id}", staticProductPackagesConditionalAttributes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProductPackagesConditionalAttributes> staticProductPackagesConditionalAttributesList = staticProductPackagesConditionalAttributesRepository.findAll();
        assertThat(staticProductPackagesConditionalAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
