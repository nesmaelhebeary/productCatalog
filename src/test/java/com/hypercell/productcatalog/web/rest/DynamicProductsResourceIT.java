package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.DynamicProducts;
import com.hypercell.productcatalog.repository.DynamicProductsRepository;

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
 * Integration tests for the {@link DynamicProductsResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DynamicProductsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DynamicProductsRepository dynamicProductsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDynamicProductsMockMvc;

    private DynamicProducts dynamicProducts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicProducts createEntity(EntityManager em) {
        DynamicProducts dynamicProducts = new DynamicProducts()
            .name(DEFAULT_NAME);
        return dynamicProducts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicProducts createUpdatedEntity(EntityManager em) {
        DynamicProducts dynamicProducts = new DynamicProducts()
            .name(UPDATED_NAME);
        return dynamicProducts;
    }

    @BeforeEach
    public void initTest() {
        dynamicProducts = createEntity(em);
    }

    @Test
    @Transactional
    public void createDynamicProducts() throws Exception {
        int databaseSizeBeforeCreate = dynamicProductsRepository.findAll().size();
        // Create the DynamicProducts
        restDynamicProductsMockMvc.perform(post("/api/dynamic-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicProducts)))
            .andExpect(status().isCreated());

        // Validate the DynamicProducts in the database
        List<DynamicProducts> dynamicProductsList = dynamicProductsRepository.findAll();
        assertThat(dynamicProductsList).hasSize(databaseSizeBeforeCreate + 1);
        DynamicProducts testDynamicProducts = dynamicProductsList.get(dynamicProductsList.size() - 1);
        assertThat(testDynamicProducts.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDynamicProductsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dynamicProductsRepository.findAll().size();

        // Create the DynamicProducts with an existing ID
        dynamicProducts.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDynamicProductsMockMvc.perform(post("/api/dynamic-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicProducts)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicProducts in the database
        List<DynamicProducts> dynamicProductsList = dynamicProductsRepository.findAll();
        assertThat(dynamicProductsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDynamicProducts() throws Exception {
        // Initialize the database
        dynamicProductsRepository.saveAndFlush(dynamicProducts);

        // Get all the dynamicProductsList
        restDynamicProductsMockMvc.perform(get("/api/dynamic-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dynamicProducts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDynamicProducts() throws Exception {
        // Initialize the database
        dynamicProductsRepository.saveAndFlush(dynamicProducts);

        // Get the dynamicProducts
        restDynamicProductsMockMvc.perform(get("/api/dynamic-products/{id}", dynamicProducts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dynamicProducts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDynamicProducts() throws Exception {
        // Get the dynamicProducts
        restDynamicProductsMockMvc.perform(get("/api/dynamic-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDynamicProducts() throws Exception {
        // Initialize the database
        dynamicProductsRepository.saveAndFlush(dynamicProducts);

        int databaseSizeBeforeUpdate = dynamicProductsRepository.findAll().size();

        // Update the dynamicProducts
        DynamicProducts updatedDynamicProducts = dynamicProductsRepository.findById(dynamicProducts.getId()).get();
        // Disconnect from session so that the updates on updatedDynamicProducts are not directly saved in db
        em.detach(updatedDynamicProducts);
        updatedDynamicProducts
            .name(UPDATED_NAME);

        restDynamicProductsMockMvc.perform(put("/api/dynamic-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDynamicProducts)))
            .andExpect(status().isOk());

        // Validate the DynamicProducts in the database
        List<DynamicProducts> dynamicProductsList = dynamicProductsRepository.findAll();
        assertThat(dynamicProductsList).hasSize(databaseSizeBeforeUpdate);
        DynamicProducts testDynamicProducts = dynamicProductsList.get(dynamicProductsList.size() - 1);
        assertThat(testDynamicProducts.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDynamicProducts() throws Exception {
        int databaseSizeBeforeUpdate = dynamicProductsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDynamicProductsMockMvc.perform(put("/api/dynamic-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicProducts)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicProducts in the database
        List<DynamicProducts> dynamicProductsList = dynamicProductsRepository.findAll();
        assertThat(dynamicProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDynamicProducts() throws Exception {
        // Initialize the database
        dynamicProductsRepository.saveAndFlush(dynamicProducts);

        int databaseSizeBeforeDelete = dynamicProductsRepository.findAll().size();

        // Delete the dynamicProducts
        restDynamicProductsMockMvc.perform(delete("/api/dynamic-products/{id}", dynamicProducts.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DynamicProducts> dynamicProductsList = dynamicProductsRepository.findAll();
        assertThat(dynamicProductsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
