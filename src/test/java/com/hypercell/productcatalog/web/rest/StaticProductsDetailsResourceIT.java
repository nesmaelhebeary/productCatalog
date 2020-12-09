package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProductsDetails;
import com.hypercell.productcatalog.repository.StaticProductsDetailsRepository;

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

import com.hypercell.productcatalog.domain.enumeration.ResourceType;
/**
 * Integration tests for the {@link StaticProductsDetailsResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductsDetailsResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final String DEFAULT_RESOURCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_VALUE = "BBBBBBBBBB";

    private static final ResourceType DEFAULT_RESOURCE_TYPE = ResourceType.DA;
    private static final ResourceType UPDATED_RESOURCE_TYPE = ResourceType.OFFER;

    @Autowired
    private StaticProductsDetailsRepository staticProductsDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductsDetailsMockMvc;

    private StaticProductsDetails staticProductsDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductsDetails createEntity(EntityManager em) {
        StaticProductsDetails staticProductsDetails = new StaticProductsDetails()
            .productId(DEFAULT_PRODUCT_ID)
            .resourceId(DEFAULT_RESOURCE_ID)
            .resourceValue(DEFAULT_RESOURCE_VALUE)
            .resourceType(DEFAULT_RESOURCE_TYPE);
        return staticProductsDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductsDetails createUpdatedEntity(EntityManager em) {
        StaticProductsDetails staticProductsDetails = new StaticProductsDetails()
            .productId(UPDATED_PRODUCT_ID)
            .resourceId(UPDATED_RESOURCE_ID)
            .resourceValue(UPDATED_RESOURCE_VALUE)
            .resourceType(UPDATED_RESOURCE_TYPE);
        return staticProductsDetails;
    }

    @BeforeEach
    public void initTest() {
        staticProductsDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProductsDetails() throws Exception {
        int databaseSizeBeforeCreate = staticProductsDetailsRepository.findAll().size();
        // Create the StaticProductsDetails
        restStaticProductsDetailsMockMvc.perform(post("/api/static-products-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductsDetails)))
            .andExpect(status().isCreated());

        // Validate the StaticProductsDetails in the database
        List<StaticProductsDetails> staticProductsDetailsList = staticProductsDetailsRepository.findAll();
        assertThat(staticProductsDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProductsDetails testStaticProductsDetails = staticProductsDetailsList.get(staticProductsDetailsList.size() - 1);
        assertThat(testStaticProductsDetails.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testStaticProductsDetails.getResourceId()).isEqualTo(DEFAULT_RESOURCE_ID);
        assertThat(testStaticProductsDetails.getResourceValue()).isEqualTo(DEFAULT_RESOURCE_VALUE);
        assertThat(testStaticProductsDetails.getResourceType()).isEqualTo(DEFAULT_RESOURCE_TYPE);
    }

    @Test
    @Transactional
    public void createStaticProductsDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductsDetailsRepository.findAll().size();

        // Create the StaticProductsDetails with an existing ID
        staticProductsDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductsDetailsMockMvc.perform(post("/api/static-products-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductsDetails)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductsDetails in the database
        List<StaticProductsDetails> staticProductsDetailsList = staticProductsDetailsRepository.findAll();
        assertThat(staticProductsDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProductsDetails() throws Exception {
        // Initialize the database
        staticProductsDetailsRepository.saveAndFlush(staticProductsDetails);

        // Get all the staticProductsDetailsList
        restStaticProductsDetailsMockMvc.perform(get("/api/static-products-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProductsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].resourceId").value(hasItem(DEFAULT_RESOURCE_ID)))
            .andExpect(jsonPath("$.[*].resourceValue").value(hasItem(DEFAULT_RESOURCE_VALUE)))
            .andExpect(jsonPath("$.[*].resourceType").value(hasItem(DEFAULT_RESOURCE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getStaticProductsDetails() throws Exception {
        // Initialize the database
        staticProductsDetailsRepository.saveAndFlush(staticProductsDetails);

        // Get the staticProductsDetails
        restStaticProductsDetailsMockMvc.perform(get("/api/static-products-details/{id}", staticProductsDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProductsDetails.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.resourceId").value(DEFAULT_RESOURCE_ID))
            .andExpect(jsonPath("$.resourceValue").value(DEFAULT_RESOURCE_VALUE))
            .andExpect(jsonPath("$.resourceType").value(DEFAULT_RESOURCE_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProductsDetails() throws Exception {
        // Get the staticProductsDetails
        restStaticProductsDetailsMockMvc.perform(get("/api/static-products-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProductsDetails() throws Exception {
        // Initialize the database
        staticProductsDetailsRepository.saveAndFlush(staticProductsDetails);

        int databaseSizeBeforeUpdate = staticProductsDetailsRepository.findAll().size();

        // Update the staticProductsDetails
        StaticProductsDetails updatedStaticProductsDetails = staticProductsDetailsRepository.findById(staticProductsDetails.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProductsDetails are not directly saved in db
        em.detach(updatedStaticProductsDetails);
        updatedStaticProductsDetails
            .productId(UPDATED_PRODUCT_ID)
            .resourceId(UPDATED_RESOURCE_ID)
            .resourceValue(UPDATED_RESOURCE_VALUE)
            .resourceType(UPDATED_RESOURCE_TYPE);

        restStaticProductsDetailsMockMvc.perform(put("/api/static-products-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProductsDetails)))
            .andExpect(status().isOk());

        // Validate the StaticProductsDetails in the database
        List<StaticProductsDetails> staticProductsDetailsList = staticProductsDetailsRepository.findAll();
        assertThat(staticProductsDetailsList).hasSize(databaseSizeBeforeUpdate);
        StaticProductsDetails testStaticProductsDetails = staticProductsDetailsList.get(staticProductsDetailsList.size() - 1);
        assertThat(testStaticProductsDetails.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testStaticProductsDetails.getResourceId()).isEqualTo(UPDATED_RESOURCE_ID);
        assertThat(testStaticProductsDetails.getResourceValue()).isEqualTo(UPDATED_RESOURCE_VALUE);
        assertThat(testStaticProductsDetails.getResourceType()).isEqualTo(UPDATED_RESOURCE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProductsDetails() throws Exception {
        int databaseSizeBeforeUpdate = staticProductsDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductsDetailsMockMvc.perform(put("/api/static-products-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductsDetails)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductsDetails in the database
        List<StaticProductsDetails> staticProductsDetailsList = staticProductsDetailsRepository.findAll();
        assertThat(staticProductsDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProductsDetails() throws Exception {
        // Initialize the database
        staticProductsDetailsRepository.saveAndFlush(staticProductsDetails);

        int databaseSizeBeforeDelete = staticProductsDetailsRepository.findAll().size();

        // Delete the staticProductsDetails
        restStaticProductsDetailsMockMvc.perform(delete("/api/static-products-details/{id}", staticProductsDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProductsDetails> staticProductsDetailsList = staticProductsDetailsRepository.findAll();
        assertThat(staticProductsDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
