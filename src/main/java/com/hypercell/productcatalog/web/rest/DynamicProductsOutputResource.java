package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.DynamicProductsOutput;
import com.hypercell.productcatalog.repository.DynamicProductsOutputRepository;
import com.hypercell.productcatalog.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hypercell.productcatalog.domain.DynamicProductsOutput}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DynamicProductsOutputResource {

    private final Logger log = LoggerFactory.getLogger(DynamicProductsOutputResource.class);

    private static final String ENTITY_NAME = "productCatalogDynamicProductsOutput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DynamicProductsOutputRepository dynamicProductsOutputRepository;

    public DynamicProductsOutputResource(DynamicProductsOutputRepository dynamicProductsOutputRepository) {
        this.dynamicProductsOutputRepository = dynamicProductsOutputRepository;
    }

    /**
     * {@code POST  /dynamic-products-outputs} : Create a new dynamicProductsOutput.
     *
     * @param dynamicProductsOutput the dynamicProductsOutput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dynamicProductsOutput, or with status {@code 400 (Bad Request)} if the dynamicProductsOutput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dynamic-products-outputs")
    public ResponseEntity<DynamicProductsOutput> createDynamicProductsOutput(@RequestBody DynamicProductsOutput dynamicProductsOutput) throws URISyntaxException {
        log.debug("REST request to save DynamicProductsOutput : {}", dynamicProductsOutput);
        if (dynamicProductsOutput.getId() != null) {
            throw new BadRequestAlertException("A new dynamicProductsOutput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DynamicProductsOutput result = dynamicProductsOutputRepository.save(dynamicProductsOutput);
        return ResponseEntity.created(new URI("/api/dynamic-products-outputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dynamic-products-outputs} : Updates an existing dynamicProductsOutput.
     *
     * @param dynamicProductsOutput the dynamicProductsOutput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dynamicProductsOutput,
     * or with status {@code 400 (Bad Request)} if the dynamicProductsOutput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dynamicProductsOutput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dynamic-products-outputs")
    public ResponseEntity<DynamicProductsOutput> updateDynamicProductsOutput(@RequestBody DynamicProductsOutput dynamicProductsOutput) throws URISyntaxException {
        log.debug("REST request to update DynamicProductsOutput : {}", dynamicProductsOutput);
        if (dynamicProductsOutput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DynamicProductsOutput result = dynamicProductsOutputRepository.save(dynamicProductsOutput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dynamicProductsOutput.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dynamic-products-outputs} : get all the dynamicProductsOutputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dynamicProductsOutputs in body.
     */
    @GetMapping("/dynamic-products-outputs")
    public List<DynamicProductsOutput> getAllDynamicProductsOutputs() {
        log.debug("REST request to get all DynamicProductsOutputs");
        return dynamicProductsOutputRepository.findAll();
    }

    /**
     * {@code GET  /dynamic-products-outputs/:id} : get the "id" dynamicProductsOutput.
     *
     * @param id the id of the dynamicProductsOutput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dynamicProductsOutput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dynamic-products-outputs/{id}")
    public ResponseEntity<DynamicProductsOutput> getDynamicProductsOutput(@PathVariable Long id) {
        log.debug("REST request to get DynamicProductsOutput : {}", id);
        Optional<DynamicProductsOutput> dynamicProductsOutput = dynamicProductsOutputRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dynamicProductsOutput);
    }

    /**
     * {@code DELETE  /dynamic-products-outputs/:id} : delete the "id" dynamicProductsOutput.
     *
     * @param id the id of the dynamicProductsOutput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dynamic-products-outputs/{id}")
    public ResponseEntity<Void> deleteDynamicProductsOutput(@PathVariable Long id) {
        log.debug("REST request to delete DynamicProductsOutput : {}", id);
        dynamicProductsOutputRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
