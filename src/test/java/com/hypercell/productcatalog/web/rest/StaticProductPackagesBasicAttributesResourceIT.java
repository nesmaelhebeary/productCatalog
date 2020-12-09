package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProductPackagesBasicAttributes;
import com.hypercell.productcatalog.repository.StaticProductPackagesBasicAttributesRepository;

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
 * Integration tests for the {@link StaticProductPackagesBasicAttributesResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductPackagesBasicAttributesResourceIT {

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PACKAGE_ID = 1L;
    private static final Long UPDATED_PACKAGE_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    @Autowired
    private StaticProductPackagesBasicAttributesRepository staticProductPackagesBasicAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductPackagesBasicAttributesMockMvc;

    private StaticProductPackagesBasicAttributes staticProductPackagesBasicAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductPackagesBasicAttributes createEntity(EntityManager em) {
        StaticProductPackagesBasicAttributes staticProductPackagesBasicAttributes = new StaticProductPackagesBasicAttributes()
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .packageId(DEFAULT_PACKAGE_ID)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return staticProductPackagesBasicAttributes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductPackagesBasicAttributes createUpdatedEntity(EntityManager em) {
        StaticProductPackagesBasicAttributes staticProductPackagesBasicAttributes = new StaticProductPackagesBasicAttributes()
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .packageId(UPDATED_PACKAGE_ID)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return staticProductPackagesBasicAttributes;
    }

    @BeforeEach
    public void initTest() {
        staticProductPackagesBasicAttributes = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProductPackagesBasicAttributes() throws Exception {
        int databaseSizeBeforeCreate = staticProductPackagesBasicAttributesRepository.findAll().size();
        // Create the StaticProductPackagesBasicAttributes
        restStaticProductPackagesBasicAttributesMockMvc.perform(post("/api/static-product-packages-basic-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductPackagesBasicAttributes)))
            .andExpect(status().isCreated());

        // Validate the StaticProductPackagesBasicAttributes in the database
        List<StaticProductPackagesBasicAttributes> staticProductPackagesBasicAttributesList = staticProductPackagesBasicAttributesRepository.findAll();
        assertThat(staticProductPackagesBasicAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProductPackagesBasicAttributes testStaticProductPackagesBasicAttributes = staticProductPackagesBasicAttributesList.get(staticProductPackagesBasicAttributesList.size() - 1);
        assertThat(testStaticProductPackagesBasicAttributes.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testStaticProductPackagesBasicAttributes.getPackageId()).isEqualTo(DEFAULT_PACKAGE_ID);
        assertThat(testStaticProductPackagesBasicAttributes.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void createStaticProductPackagesBasicAttributesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductPackagesBasicAttributesRepository.findAll().size();

        // Create the StaticProductPackagesBasicAttributes with an existing ID
        staticProductPackagesBasicAttributes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductPackagesBasicAttributesMockMvc.perform(post("/api/static-product-packages-basic-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductPackagesBasicAttributes)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductPackagesBasicAttributes in the database
        List<StaticProductPackagesBasicAttributes> staticProductPackagesBasicAttributesList = staticProductPackagesBasicAttributesRepository.findAll();
        assertThat(staticProductPackagesBasicAttributesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProductPackagesBasicAttributes() throws Exception {
        // Initialize the database
        staticProductPackagesBasicAttributesRepository.saveAndFlush(staticProductPackagesBasicAttributes);

        // Get all the staticProductPackagesBasicAttributesList
        restStaticProductPackagesBasicAttributesMockMvc.perform(get("/api/static-product-packages-basic-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProductPackagesBasicAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].packageId").value(hasItem(DEFAULT_PACKAGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }
    
    @Test
    @Transactional
    public void getStaticProductPackagesBasicAttributes() throws Exception {
        // Initialize the database
        staticProductPackagesBasicAttributesRepository.saveAndFlush(staticProductPackagesBasicAttributes);

        // Get the staticProductPackagesBasicAttributes
        restStaticProductPackagesBasicAttributesMockMvc.perform(get("/api/static-product-packages-basic-attributes/{id}", staticProductPackagesBasicAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProductPackagesBasicAttributes.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.packageId").value(DEFAULT_PACKAGE_ID.intValue()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProductPackagesBasicAttributes() throws Exception {
        // Get the staticProductPackagesBasicAttributes
        restStaticProductPackagesBasicAttributesMockMvc.perform(get("/api/static-product-packages-basic-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProductPackagesBasicAttributes() throws Exception {
        // Initialize the database
        staticProductPackagesBasicAttributesRepository.saveAndFlush(staticProductPackagesBasicAttributes);

        int databaseSizeBeforeUpdate = staticProductPackagesBasicAttributesRepository.findAll().size();

        // Update the staticProductPackagesBasicAttributes
        StaticProductPackagesBasicAttributes updatedStaticProductPackagesBasicAttributes = staticProductPackagesBasicAttributesRepository.findById(staticProductPackagesBasicAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProductPackagesBasicAttributes are not directly saved in db
        em.detach(updatedStaticProductPackagesBasicAttributes);
        updatedStaticProductPackagesBasicAttributes
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .packageId(UPDATED_PACKAGE_ID)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restStaticProductPackagesBasicAttributesMockMvc.perform(put("/api/static-product-packages-basic-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProductPackagesBasicAttributes)))
            .andExpect(status().isOk());

        // Validate the StaticProductPackagesBasicAttributes in the database
        List<StaticProductPackagesBasicAttributes> staticProductPackagesBasicAttributesList = staticProductPackagesBasicAttributesRepository.findAll();
        assertThat(staticProductPackagesBasicAttributesList).hasSize(databaseSizeBeforeUpdate);
        StaticProductPackagesBasicAttributes testStaticProductPackagesBasicAttributes = staticProductPackagesBasicAttributesList.get(staticProductPackagesBasicAttributesList.size() - 1);
        assertThat(testStaticProductPackagesBasicAttributes.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testStaticProductPackagesBasicAttributes.getPackageId()).isEqualTo(UPDATED_PACKAGE_ID);
        assertThat(testStaticProductPackagesBasicAttributes.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProductPackagesBasicAttributes() throws Exception {
        int databaseSizeBeforeUpdate = staticProductPackagesBasicAttributesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductPackagesBasicAttributesMockMvc.perform(put("/api/static-product-packages-basic-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductPackagesBasicAttributes)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductPackagesBasicAttributes in the database
        List<StaticProductPackagesBasicAttributes> staticProductPackagesBasicAttributesList = staticProductPackagesBasicAttributesRepository.findAll();
        assertThat(staticProductPackagesBasicAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProductPackagesBasicAttributes() throws Exception {
        // Initialize the database
        staticProductPackagesBasicAttributesRepository.saveAndFlush(staticProductPackagesBasicAttributes);

        int databaseSizeBeforeDelete = staticProductPackagesBasicAttributesRepository.findAll().size();

        // Delete the staticProductPackagesBasicAttributes
        restStaticProductPackagesBasicAttributesMockMvc.perform(delete("/api/static-product-packages-basic-attributes/{id}", staticProductPackagesBasicAttributes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProductPackagesBasicAttributes> staticProductPackagesBasicAttributesList = staticProductPackagesBasicAttributesRepository.findAll();
        assertThat(staticProductPackagesBasicAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
