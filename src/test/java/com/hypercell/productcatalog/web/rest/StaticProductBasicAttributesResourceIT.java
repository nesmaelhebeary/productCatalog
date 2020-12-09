package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProductBasicAttributes;
import com.hypercell.productcatalog.repository.StaticProductBasicAttributesRepository;

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
 * Integration tests for the {@link StaticProductBasicAttributesResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductBasicAttributesResourceIT {

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    @Autowired
    private StaticProductBasicAttributesRepository staticProductBasicAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductBasicAttributesMockMvc;

    private StaticProductBasicAttributes staticProductBasicAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductBasicAttributes createEntity(EntityManager em) {
        StaticProductBasicAttributes staticProductBasicAttributes = new StaticProductBasicAttributes()
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .productId(DEFAULT_PRODUCT_ID)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return staticProductBasicAttributes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductBasicAttributes createUpdatedEntity(EntityManager em) {
        StaticProductBasicAttributes staticProductBasicAttributes = new StaticProductBasicAttributes()
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .productId(UPDATED_PRODUCT_ID)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return staticProductBasicAttributes;
    }

    @BeforeEach
    public void initTest() {
        staticProductBasicAttributes = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProductBasicAttributes() throws Exception {
        int databaseSizeBeforeCreate = staticProductBasicAttributesRepository.findAll().size();
        // Create the StaticProductBasicAttributes
        restStaticProductBasicAttributesMockMvc.perform(post("/api/static-product-basic-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductBasicAttributes)))
            .andExpect(status().isCreated());

        // Validate the StaticProductBasicAttributes in the database
        List<StaticProductBasicAttributes> staticProductBasicAttributesList = staticProductBasicAttributesRepository.findAll();
        assertThat(staticProductBasicAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProductBasicAttributes testStaticProductBasicAttributes = staticProductBasicAttributesList.get(staticProductBasicAttributesList.size() - 1);
        assertThat(testStaticProductBasicAttributes.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testStaticProductBasicAttributes.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testStaticProductBasicAttributes.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void createStaticProductBasicAttributesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductBasicAttributesRepository.findAll().size();

        // Create the StaticProductBasicAttributes with an existing ID
        staticProductBasicAttributes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductBasicAttributesMockMvc.perform(post("/api/static-product-basic-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductBasicAttributes)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductBasicAttributes in the database
        List<StaticProductBasicAttributes> staticProductBasicAttributesList = staticProductBasicAttributesRepository.findAll();
        assertThat(staticProductBasicAttributesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProductBasicAttributes() throws Exception {
        // Initialize the database
        staticProductBasicAttributesRepository.saveAndFlush(staticProductBasicAttributes);

        // Get all the staticProductBasicAttributesList
        restStaticProductBasicAttributesMockMvc.perform(get("/api/static-product-basic-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProductBasicAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }
    
    @Test
    @Transactional
    public void getStaticProductBasicAttributes() throws Exception {
        // Initialize the database
        staticProductBasicAttributesRepository.saveAndFlush(staticProductBasicAttributes);

        // Get the staticProductBasicAttributes
        restStaticProductBasicAttributesMockMvc.perform(get("/api/static-product-basic-attributes/{id}", staticProductBasicAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProductBasicAttributes.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProductBasicAttributes() throws Exception {
        // Get the staticProductBasicAttributes
        restStaticProductBasicAttributesMockMvc.perform(get("/api/static-product-basic-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProductBasicAttributes() throws Exception {
        // Initialize the database
        staticProductBasicAttributesRepository.saveAndFlush(staticProductBasicAttributes);

        int databaseSizeBeforeUpdate = staticProductBasicAttributesRepository.findAll().size();

        // Update the staticProductBasicAttributes
        StaticProductBasicAttributes updatedStaticProductBasicAttributes = staticProductBasicAttributesRepository.findById(staticProductBasicAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProductBasicAttributes are not directly saved in db
        em.detach(updatedStaticProductBasicAttributes);
        updatedStaticProductBasicAttributes
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .productId(UPDATED_PRODUCT_ID)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restStaticProductBasicAttributesMockMvc.perform(put("/api/static-product-basic-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProductBasicAttributes)))
            .andExpect(status().isOk());

        // Validate the StaticProductBasicAttributes in the database
        List<StaticProductBasicAttributes> staticProductBasicAttributesList = staticProductBasicAttributesRepository.findAll();
        assertThat(staticProductBasicAttributesList).hasSize(databaseSizeBeforeUpdate);
        StaticProductBasicAttributes testStaticProductBasicAttributes = staticProductBasicAttributesList.get(staticProductBasicAttributesList.size() - 1);
        assertThat(testStaticProductBasicAttributes.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testStaticProductBasicAttributes.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testStaticProductBasicAttributes.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProductBasicAttributes() throws Exception {
        int databaseSizeBeforeUpdate = staticProductBasicAttributesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductBasicAttributesMockMvc.perform(put("/api/static-product-basic-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductBasicAttributes)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductBasicAttributes in the database
        List<StaticProductBasicAttributes> staticProductBasicAttributesList = staticProductBasicAttributesRepository.findAll();
        assertThat(staticProductBasicAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProductBasicAttributes() throws Exception {
        // Initialize the database
        staticProductBasicAttributesRepository.saveAndFlush(staticProductBasicAttributes);

        int databaseSizeBeforeDelete = staticProductBasicAttributesRepository.findAll().size();

        // Delete the staticProductBasicAttributes
        restStaticProductBasicAttributesMockMvc.perform(delete("/api/static-product-basic-attributes/{id}", staticProductBasicAttributes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProductBasicAttributes> staticProductBasicAttributesList = staticProductBasicAttributesRepository.findAll();
        assertThat(staticProductBasicAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
