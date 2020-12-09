package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProductConditionalAttributes;
import com.hypercell.productcatalog.repository.StaticProductConditionalAttributesRepository;

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
 * Integration tests for the {@link StaticProductConditionalAttributesResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductConditionalAttributesResourceIT {

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_CONDITION_ID = 1L;
    private static final Long UPDATED_CONDITION_ID = 2L;

    @Autowired
    private StaticProductConditionalAttributesRepository staticProductConditionalAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductConditionalAttributesMockMvc;

    private StaticProductConditionalAttributes staticProductConditionalAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductConditionalAttributes createEntity(EntityManager em) {
        StaticProductConditionalAttributes staticProductConditionalAttributes = new StaticProductConditionalAttributes()
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .productId(DEFAULT_PRODUCT_ID)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE)
            .conditionId(DEFAULT_CONDITION_ID);
        return staticProductConditionalAttributes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductConditionalAttributes createUpdatedEntity(EntityManager em) {
        StaticProductConditionalAttributes staticProductConditionalAttributes = new StaticProductConditionalAttributes()
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .productId(UPDATED_PRODUCT_ID)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .conditionId(UPDATED_CONDITION_ID);
        return staticProductConditionalAttributes;
    }

    @BeforeEach
    public void initTest() {
        staticProductConditionalAttributes = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProductConditionalAttributes() throws Exception {
        int databaseSizeBeforeCreate = staticProductConditionalAttributesRepository.findAll().size();
        // Create the StaticProductConditionalAttributes
        restStaticProductConditionalAttributesMockMvc.perform(post("/api/static-product-conditional-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductConditionalAttributes)))
            .andExpect(status().isCreated());

        // Validate the StaticProductConditionalAttributes in the database
        List<StaticProductConditionalAttributes> staticProductConditionalAttributesList = staticProductConditionalAttributesRepository.findAll();
        assertThat(staticProductConditionalAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProductConditionalAttributes testStaticProductConditionalAttributes = staticProductConditionalAttributesList.get(staticProductConditionalAttributesList.size() - 1);
        assertThat(testStaticProductConditionalAttributes.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testStaticProductConditionalAttributes.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testStaticProductConditionalAttributes.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
        assertThat(testStaticProductConditionalAttributes.getConditionId()).isEqualTo(DEFAULT_CONDITION_ID);
    }

    @Test
    @Transactional
    public void createStaticProductConditionalAttributesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductConditionalAttributesRepository.findAll().size();

        // Create the StaticProductConditionalAttributes with an existing ID
        staticProductConditionalAttributes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductConditionalAttributesMockMvc.perform(post("/api/static-product-conditional-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductConditionalAttributes)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductConditionalAttributes in the database
        List<StaticProductConditionalAttributes> staticProductConditionalAttributesList = staticProductConditionalAttributesRepository.findAll();
        assertThat(staticProductConditionalAttributesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProductConditionalAttributes() throws Exception {
        // Initialize the database
        staticProductConditionalAttributesRepository.saveAndFlush(staticProductConditionalAttributes);

        // Get all the staticProductConditionalAttributesList
        restStaticProductConditionalAttributesMockMvc.perform(get("/api/static-product-conditional-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProductConditionalAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)))
            .andExpect(jsonPath("$.[*].conditionId").value(hasItem(DEFAULT_CONDITION_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getStaticProductConditionalAttributes() throws Exception {
        // Initialize the database
        staticProductConditionalAttributesRepository.saveAndFlush(staticProductConditionalAttributes);

        // Get the staticProductConditionalAttributes
        restStaticProductConditionalAttributesMockMvc.perform(get("/api/static-product-conditional-attributes/{id}", staticProductConditionalAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProductConditionalAttributes.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE))
            .andExpect(jsonPath("$.conditionId").value(DEFAULT_CONDITION_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProductConditionalAttributes() throws Exception {
        // Get the staticProductConditionalAttributes
        restStaticProductConditionalAttributesMockMvc.perform(get("/api/static-product-conditional-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProductConditionalAttributes() throws Exception {
        // Initialize the database
        staticProductConditionalAttributesRepository.saveAndFlush(staticProductConditionalAttributes);

        int databaseSizeBeforeUpdate = staticProductConditionalAttributesRepository.findAll().size();

        // Update the staticProductConditionalAttributes
        StaticProductConditionalAttributes updatedStaticProductConditionalAttributes = staticProductConditionalAttributesRepository.findById(staticProductConditionalAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProductConditionalAttributes are not directly saved in db
        em.detach(updatedStaticProductConditionalAttributes);
        updatedStaticProductConditionalAttributes
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .productId(UPDATED_PRODUCT_ID)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .conditionId(UPDATED_CONDITION_ID);

        restStaticProductConditionalAttributesMockMvc.perform(put("/api/static-product-conditional-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProductConditionalAttributes)))
            .andExpect(status().isOk());

        // Validate the StaticProductConditionalAttributes in the database
        List<StaticProductConditionalAttributes> staticProductConditionalAttributesList = staticProductConditionalAttributesRepository.findAll();
        assertThat(staticProductConditionalAttributesList).hasSize(databaseSizeBeforeUpdate);
        StaticProductConditionalAttributes testStaticProductConditionalAttributes = staticProductConditionalAttributesList.get(staticProductConditionalAttributesList.size() - 1);
        assertThat(testStaticProductConditionalAttributes.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testStaticProductConditionalAttributes.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testStaticProductConditionalAttributes.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
        assertThat(testStaticProductConditionalAttributes.getConditionId()).isEqualTo(UPDATED_CONDITION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProductConditionalAttributes() throws Exception {
        int databaseSizeBeforeUpdate = staticProductConditionalAttributesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductConditionalAttributesMockMvc.perform(put("/api/static-product-conditional-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductConditionalAttributes)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductConditionalAttributes in the database
        List<StaticProductConditionalAttributes> staticProductConditionalAttributesList = staticProductConditionalAttributesRepository.findAll();
        assertThat(staticProductConditionalAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProductConditionalAttributes() throws Exception {
        // Initialize the database
        staticProductConditionalAttributesRepository.saveAndFlush(staticProductConditionalAttributes);

        int databaseSizeBeforeDelete = staticProductConditionalAttributesRepository.findAll().size();

        // Delete the staticProductConditionalAttributes
        restStaticProductConditionalAttributesMockMvc.perform(delete("/api/static-product-conditional-attributes/{id}", staticProductConditionalAttributes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProductConditionalAttributes> staticProductConditionalAttributesList = staticProductConditionalAttributesRepository.findAll();
        assertThat(staticProductConditionalAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
