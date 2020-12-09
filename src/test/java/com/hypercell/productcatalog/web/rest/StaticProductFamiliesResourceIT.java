package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProductFamilies;
import com.hypercell.productcatalog.repository.StaticProductFamiliesRepository;

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

import com.hypercell.productcatalog.domain.enumeration.StaticProductType;
/**
 * Integration tests for the {@link StaticProductFamiliesResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductFamiliesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final StaticProductType DEFAULT_PRODUCT_TYPE = StaticProductType.ServiceClass;
    private static final StaticProductType UPDATED_PRODUCT_TYPE = StaticProductType.Promotion;

    @Autowired
    private StaticProductFamiliesRepository staticProductFamiliesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductFamiliesMockMvc;

    private StaticProductFamilies staticProductFamilies;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductFamilies createEntity(EntityManager em) {
        StaticProductFamilies staticProductFamilies = new StaticProductFamilies()
            .name(DEFAULT_NAME)
            .productType(DEFAULT_PRODUCT_TYPE);
        return staticProductFamilies;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductFamilies createUpdatedEntity(EntityManager em) {
        StaticProductFamilies staticProductFamilies = new StaticProductFamilies()
            .name(UPDATED_NAME)
            .productType(UPDATED_PRODUCT_TYPE);
        return staticProductFamilies;
    }

    @BeforeEach
    public void initTest() {
        staticProductFamilies = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProductFamilies() throws Exception {
        int databaseSizeBeforeCreate = staticProductFamiliesRepository.findAll().size();
        // Create the StaticProductFamilies
        restStaticProductFamiliesMockMvc.perform(post("/api/static-product-families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductFamilies)))
            .andExpect(status().isCreated());

        // Validate the StaticProductFamilies in the database
        List<StaticProductFamilies> staticProductFamiliesList = staticProductFamiliesRepository.findAll();
        assertThat(staticProductFamiliesList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProductFamilies testStaticProductFamilies = staticProductFamiliesList.get(staticProductFamiliesList.size() - 1);
        assertThat(testStaticProductFamilies.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStaticProductFamilies.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void createStaticProductFamiliesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductFamiliesRepository.findAll().size();

        // Create the StaticProductFamilies with an existing ID
        staticProductFamilies.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductFamiliesMockMvc.perform(post("/api/static-product-families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductFamilies)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductFamilies in the database
        List<StaticProductFamilies> staticProductFamiliesList = staticProductFamiliesRepository.findAll();
        assertThat(staticProductFamiliesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProductFamilies() throws Exception {
        // Initialize the database
        staticProductFamiliesRepository.saveAndFlush(staticProductFamilies);

        // Get all the staticProductFamiliesList
        restStaticProductFamiliesMockMvc.perform(get("/api/static-product-families?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProductFamilies.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getStaticProductFamilies() throws Exception {
        // Initialize the database
        staticProductFamiliesRepository.saveAndFlush(staticProductFamilies);

        // Get the staticProductFamilies
        restStaticProductFamiliesMockMvc.perform(get("/api/static-product-families/{id}", staticProductFamilies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProductFamilies.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProductFamilies() throws Exception {
        // Get the staticProductFamilies
        restStaticProductFamiliesMockMvc.perform(get("/api/static-product-families/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProductFamilies() throws Exception {
        // Initialize the database
        staticProductFamiliesRepository.saveAndFlush(staticProductFamilies);

        int databaseSizeBeforeUpdate = staticProductFamiliesRepository.findAll().size();

        // Update the staticProductFamilies
        StaticProductFamilies updatedStaticProductFamilies = staticProductFamiliesRepository.findById(staticProductFamilies.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProductFamilies are not directly saved in db
        em.detach(updatedStaticProductFamilies);
        updatedStaticProductFamilies
            .name(UPDATED_NAME)
            .productType(UPDATED_PRODUCT_TYPE);

        restStaticProductFamiliesMockMvc.perform(put("/api/static-product-families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProductFamilies)))
            .andExpect(status().isOk());

        // Validate the StaticProductFamilies in the database
        List<StaticProductFamilies> staticProductFamiliesList = staticProductFamiliesRepository.findAll();
        assertThat(staticProductFamiliesList).hasSize(databaseSizeBeforeUpdate);
        StaticProductFamilies testStaticProductFamilies = staticProductFamiliesList.get(staticProductFamiliesList.size() - 1);
        assertThat(testStaticProductFamilies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStaticProductFamilies.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProductFamilies() throws Exception {
        int databaseSizeBeforeUpdate = staticProductFamiliesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductFamiliesMockMvc.perform(put("/api/static-product-families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductFamilies)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductFamilies in the database
        List<StaticProductFamilies> staticProductFamiliesList = staticProductFamiliesRepository.findAll();
        assertThat(staticProductFamiliesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProductFamilies() throws Exception {
        // Initialize the database
        staticProductFamiliesRepository.saveAndFlush(staticProductFamilies);

        int databaseSizeBeforeDelete = staticProductFamiliesRepository.findAll().size();

        // Delete the staticProductFamilies
        restStaticProductFamiliesMockMvc.perform(delete("/api/static-product-families/{id}", staticProductFamilies.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProductFamilies> staticProductFamiliesList = staticProductFamiliesRepository.findAll();
        assertThat(staticProductFamiliesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
