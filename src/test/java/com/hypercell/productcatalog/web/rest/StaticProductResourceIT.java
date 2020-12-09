package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.StaticProduct;
import com.hypercell.productcatalog.repository.StaticProductRepository;

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
 * Integration tests for the {@link StaticProductResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StaticProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_FAMILY_ID = 1L;
    private static final Long UPDATED_FAMILY_ID = 2L;

    @Autowired
    private StaticProductRepository staticProductRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticProductMockMvc;

    private StaticProduct staticProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProduct createEntity(EntityManager em) {
        StaticProduct staticProduct = new StaticProduct()
            .name(DEFAULT_NAME)
            .familyId(DEFAULT_FAMILY_ID);
        return staticProduct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticProduct createUpdatedEntity(EntityManager em) {
        StaticProduct staticProduct = new StaticProduct()
            .name(UPDATED_NAME)
            .familyId(UPDATED_FAMILY_ID);
        return staticProduct;
    }

    @BeforeEach
    public void initTest() {
        staticProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticProduct() throws Exception {
        int databaseSizeBeforeCreate = staticProductRepository.findAll().size();
        // Create the StaticProduct
        restStaticProductMockMvc.perform(post("/api/static-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProduct)))
            .andExpect(status().isCreated());

        // Validate the StaticProduct in the database
        List<StaticProduct> staticProductList = staticProductRepository.findAll();
        assertThat(staticProductList).hasSize(databaseSizeBeforeCreate + 1);
        StaticProduct testStaticProduct = staticProductList.get(staticProductList.size() - 1);
        assertThat(testStaticProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStaticProduct.getFamilyId()).isEqualTo(DEFAULT_FAMILY_ID);
    }

    @Test
    @Transactional
    public void createStaticProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticProductRepository.findAll().size();

        // Create the StaticProduct with an existing ID
        staticProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticProductMockMvc.perform(post("/api/static-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProduct)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProduct in the database
        List<StaticProduct> staticProductList = staticProductRepository.findAll();
        assertThat(staticProductList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStaticProducts() throws Exception {
        // Initialize the database
        staticProductRepository.saveAndFlush(staticProduct);

        // Get all the staticProductList
        restStaticProductMockMvc.perform(get("/api/static-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].familyId").value(hasItem(DEFAULT_FAMILY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getStaticProduct() throws Exception {
        // Initialize the database
        staticProductRepository.saveAndFlush(staticProduct);

        // Get the staticProduct
        restStaticProductMockMvc.perform(get("/api/static-products/{id}", staticProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticProduct.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.familyId").value(DEFAULT_FAMILY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStaticProduct() throws Exception {
        // Get the staticProduct
        restStaticProductMockMvc.perform(get("/api/static-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticProduct() throws Exception {
        // Initialize the database
        staticProductRepository.saveAndFlush(staticProduct);

        int databaseSizeBeforeUpdate = staticProductRepository.findAll().size();

        // Update the staticProduct
        StaticProduct updatedStaticProduct = staticProductRepository.findById(staticProduct.getId()).get();
        // Disconnect from session so that the updates on updatedStaticProduct are not directly saved in db
        em.detach(updatedStaticProduct);
        updatedStaticProduct
            .name(UPDATED_NAME)
            .familyId(UPDATED_FAMILY_ID);

        restStaticProductMockMvc.perform(put("/api/static-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticProduct)))
            .andExpect(status().isOk());

        // Validate the StaticProduct in the database
        List<StaticProduct> staticProductList = staticProductRepository.findAll();
        assertThat(staticProductList).hasSize(databaseSizeBeforeUpdate);
        StaticProduct testStaticProduct = staticProductList.get(staticProductList.size() - 1);
        assertThat(testStaticProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStaticProduct.getFamilyId()).isEqualTo(UPDATED_FAMILY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticProduct() throws Exception {
        int databaseSizeBeforeUpdate = staticProductRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticProductMockMvc.perform(put("/api/static-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticProduct)))
            .andExpect(status().isBadRequest());

        // Validate the StaticProduct in the database
        List<StaticProduct> staticProductList = staticProductRepository.findAll();
        assertThat(staticProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticProduct() throws Exception {
        // Initialize the database
        staticProductRepository.saveAndFlush(staticProduct);

        int databaseSizeBeforeDelete = staticProductRepository.findAll().size();

        // Delete the staticProduct
        restStaticProductMockMvc.perform(delete("/api/static-products/{id}", staticProduct.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticProduct> staticProductList = staticProductRepository.findAll();
        assertThat(staticProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
