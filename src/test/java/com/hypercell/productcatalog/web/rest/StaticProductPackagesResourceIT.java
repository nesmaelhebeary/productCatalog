package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProductPackages;
import com.hypercell.productcatalog.repository.StaticProductPackagesRepository;

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
 * Integration tests for the {@link StaticProductPackagesResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductPackagesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Double DEFAULT_FEES = 1D;
    private static final Double UPDATED_FEES = 2D;

    @Autowired
    private StaticProductPackagesRepository staticProductPackagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductPackagesMockMvc;

    private StaticProductPackages staticProductPackages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductPackages createEntity(EntityManager em) {
        StaticProductPackages staticProductPackages = new StaticProductPackages()
            .name(DEFAULT_NAME)
            .productId(DEFAULT_PRODUCT_ID)
            .fees(DEFAULT_FEES);
        return staticProductPackages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProductPackages createUpdatedEntity(EntityManager em) {
        StaticProductPackages staticProductPackages = new StaticProductPackages()
            .name(UPDATED_NAME)
            .productId(UPDATED_PRODUCT_ID)
            .fees(UPDATED_FEES);
        return staticProductPackages;
    }

    @BeforeEach
    public void initTest() {
        staticProductPackages = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProductPackages() throws Exception {
        int databaseSizeBeforeCreate = staticProductPackagesRepository.findAll().size();
        // Create the StaticProductPackages
        restStaticProductPackagesMockMvc.perform(post("/api/static-product-packages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductPackages)))
            .andExpect(status().isCreated());

        // Validate the StaticProductPackages in the database
        List<StaticProductPackages> staticProductPackagesList = staticProductPackagesRepository.findAll();
        assertThat(staticProductPackagesList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProductPackages testStaticProductPackages = staticProductPackagesList.get(staticProductPackagesList.size() - 1);
        assertThat(testStaticProductPackages.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStaticProductPackages.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testStaticProductPackages.getFees()).isEqualTo(DEFAULT_FEES);
    }

    @Test
    @Transactional
    public void createStaticProductPackagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductPackagesRepository.findAll().size();

        // Create the StaticProductPackages with an existing ID
        staticProductPackages.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductPackagesMockMvc.perform(post("/api/static-product-packages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductPackages)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductPackages in the database
        List<StaticProductPackages> staticProductPackagesList = staticProductPackagesRepository.findAll();
        assertThat(staticProductPackagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProductPackages() throws Exception {
        // Initialize the database
        staticProductPackagesRepository.saveAndFlush(staticProductPackages);

        // Get all the staticProductPackagesList
        restStaticProductPackagesMockMvc.perform(get("/api/static-product-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProductPackages.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getStaticProductPackages() throws Exception {
        // Initialize the database
        staticProductPackagesRepository.saveAndFlush(staticProductPackages);

        // Get the staticProductPackages
        restStaticProductPackagesMockMvc.perform(get("/api/static-product-packages/{id}", staticProductPackages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProductPackages.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.fees").value(DEFAULT_FEES.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProductPackages() throws Exception {
        // Get the staticProductPackages
        restStaticProductPackagesMockMvc.perform(get("/api/static-product-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProductPackages() throws Exception {
        // Initialize the database
        staticProductPackagesRepository.saveAndFlush(staticProductPackages);

        int databaseSizeBeforeUpdate = staticProductPackagesRepository.findAll().size();

        // Update the staticProductPackages
        StaticProductPackages updatedStaticProductPackages = staticProductPackagesRepository.findById(staticProductPackages.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProductPackages are not directly saved in db
        em.detach(updatedStaticProductPackages);
        updatedStaticProductPackages
            .name(UPDATED_NAME)
            .productId(UPDATED_PRODUCT_ID)
            .fees(UPDATED_FEES);

        restStaticProductPackagesMockMvc.perform(put("/api/static-product-packages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProductPackages)))
            .andExpect(status().isOk());

        // Validate the StaticProductPackages in the database
        List<StaticProductPackages> staticProductPackagesList = staticProductPackagesRepository.findAll();
        assertThat(staticProductPackagesList).hasSize(databaseSizeBeforeUpdate);
        StaticProductPackages testStaticProductPackages = staticProductPackagesList.get(staticProductPackagesList.size() - 1);
        assertThat(testStaticProductPackages.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStaticProductPackages.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testStaticProductPackages.getFees()).isEqualTo(UPDATED_FEES);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProductPackages() throws Exception {
        int databaseSizeBeforeUpdate = staticProductPackagesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductPackagesMockMvc.perform(put("/api/static-product-packages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProductPackages)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProductPackages in the database
        List<StaticProductPackages> staticProductPackagesList = staticProductPackagesRepository.findAll();
        assertThat(staticProductPackagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProductPackages() throws Exception {
        // Initialize the database
        staticProductPackagesRepository.saveAndFlush(staticProductPackages);

        int databaseSizeBeforeDelete = staticProductPackagesRepository.findAll().size();

        // Delete the staticProductPackages
        restStaticProductPackagesMockMvc.perform(delete("/api/static-product-packages/{id}", staticProductPackages.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProductPackages> staticProductPackagesList = staticProductPackagesRepository.findAll();
        assertThat(staticProductPackagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
