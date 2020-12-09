package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.ProductCatalogApp;
import com.hypercell.productcatalog.domain.ExternalSystems;
import com.hypercell.productcatalog.repository.ExternalSystemsRepository;

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
 * Integration tests for the {@link ExternalSystemsResource} REST controller.
 */
@SpringBootTest(classes = ProductCatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExternalSystemsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private ExternalSystemsRepository externalSystemsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExternalSystemsMockMvc;

    private ExternalSystems externalSystems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalSystems createEntity(EntityManager em) {
        ExternalSystems externalSystems = new ExternalSystems()
            .name(DEFAULT_NAME)
            .url(DEFAULT_URL);
        return externalSystems;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalSystems createUpdatedEntity(EntityManager em) {
        ExternalSystems externalSystems = new ExternalSystems()
            .name(UPDATED_NAME)
            .url(UPDATED_URL);
        return externalSystems;
    }

    @BeforeEach
    public void initTest() {
        externalSystems = createEntity(em);
    }

    @Test
    @Transactional
    public void createExternalSystems() throws Exception {
        int databaseSizeBeforeCreate = externalSystemsRepository.findAll().size();
        // Create the ExternalSystems
        restExternalSystemsMockMvc.perform(post("/api/external-systems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(externalSystems)))
            .andExpect(status().isCreated());

        // Validate the ExternalSystems in the database
        List<ExternalSystems> externalSystemsList = externalSystemsRepository.findAll();
        assertThat(externalSystemsList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalSystems testExternalSystems = externalSystemsList.get(externalSystemsList.size() - 1);
        assertThat(testExternalSystems.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExternalSystems.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createExternalSystemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = externalSystemsRepository.findAll().size();

        // Create the ExternalSystems with an existing ID
        externalSystems.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternalSystemsMockMvc.perform(post("/api/external-systems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(externalSystems)))
            .andExpect(status().isBadRequest());

        // Validate the ExternalSystems in the database
        List<ExternalSystems> externalSystemsList = externalSystemsRepository.findAll();
        assertThat(externalSystemsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExternalSystems() throws Exception {
        // Initialize the database
        externalSystemsRepository.saveAndFlush(externalSystems);

        // Get all the externalSystemsList
        restExternalSystemsMockMvc.perform(get("/api/external-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalSystems.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getExternalSystems() throws Exception {
        // Initialize the database
        externalSystemsRepository.saveAndFlush(externalSystems);

        // Get the externalSystems
        restExternalSystemsMockMvc.perform(get("/api/external-systems/{id}", externalSystems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(externalSystems.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }
    @Test
    @Transactional
    public void getNonExistingExternalSystems() throws Exception {
        // Get the externalSystems
        restExternalSystemsMockMvc.perform(get("/api/external-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExternalSystems() throws Exception {
        // Initialize the database
        externalSystemsRepository.saveAndFlush(externalSystems);

        int databaseSizeBeforeUpdate = externalSystemsRepository.findAll().size();

        // Update the externalSystems
        ExternalSystems updatedExternalSystems = externalSystemsRepository.findById(externalSystems.getId()).get();
        // Disconnect from session so that the updates on updatedExternalSystems are not directly saved in db
        em.detach(updatedExternalSystems);
        updatedExternalSystems
            .name(UPDATED_NAME)
            .url(UPDATED_URL);

        restExternalSystemsMockMvc.perform(put("/api/external-systems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExternalSystems)))
            .andExpect(status().isOk());

        // Validate the ExternalSystems in the database
        List<ExternalSystems> externalSystemsList = externalSystemsRepository.findAll();
        assertThat(externalSystemsList).hasSize(databaseSizeBeforeUpdate);
        ExternalSystems testExternalSystems = externalSystemsList.get(externalSystemsList.size() - 1);
        assertThat(testExternalSystems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExternalSystems.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingExternalSystems() throws Exception {
        int databaseSizeBeforeUpdate = externalSystemsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternalSystemsMockMvc.perform(put("/api/external-systems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(externalSystems)))
            .andExpect(status().isBadRequest());

        // Validate the ExternalSystems in the database
        List<ExternalSystems> externalSystemsList = externalSystemsRepository.findAll();
        assertThat(externalSystemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExternalSystems() throws Exception {
        // Initialize the database
        externalSystemsRepository.saveAndFlush(externalSystems);

        int databaseSizeBeforeDelete = externalSystemsRepository.findAll().size();

        // Delete the externalSystems
        restExternalSystemsMockMvc.perform(delete("/api/external-systems/{id}", externalSystems.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExternalSystems> externalSystemsList = externalSystemsRepository.findAll();
        assertThat(externalSystemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
