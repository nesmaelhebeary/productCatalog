package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.DynamicProducts;
import com.hypercell.productcatalog.repository.DynamicProductsRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.DynamicProducts}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DynamicProductsResource {

    private final Logger log = LoggerFactory.getLogger(DynamicProductsResource.class);

    private static final String ENTITY_NAME = "productCatalogDynamicProducts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DynamicProductsRepository dynamicProductsRepository;

    public DynamicProductsResource(DynamicProductsRepository dynamicProductsRepository) {
        this.dynamicProductsRepository = dynamicProductsRepository;
    }

    /**
     * {@code POST  /dynamic-products} : Create a new dynamicProducts.
     *
     * @param dynamicProducts the dynamicProducts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dynamicProducts, or with status {@code 400 (Bad Request)} if the dynamicProducts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dynamic-products")
    public ResponseEntity<DynamicProducts> createDynamicProducts(@RequestBody DynamicProducts dynamicProducts) throws URISyntaxException {
        log.debug("REST request to save DynamicProducts : {}", dynamicProducts);
        if (dynamicProducts.getId() != null) {
            throw new BadRequestAlertException("A new dynamicProducts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DynamicProducts result = dynamicProductsRepository.save(dynamicProducts);
        return ResponseEntity.created(new URI("/api/dynamic-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dynamic-products} : Updates an existing dynamicProducts.
     *
     * @param dynamicProducts the dynamicProducts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dynamicProducts,
     * or with status {@code 400 (Bad Request)} if the dynamicProducts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dynamicProducts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dynamic-products")
    public ResponseEntity<DynamicProducts> updateDynamicProducts(@RequestBody DynamicProducts dynamicProducts) throws URISyntaxException {
        log.debug("REST request to update DynamicProducts : {}", dynamicProducts);
        if (dynamicProducts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DynamicProducts result = dynamicProductsRepository.save(dynamicProducts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dynamicProducts.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dynamic-products} : get all the dynamicProducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dynamicProducts in body.
     */
    @GetMapping("/dynamic-products")
    public List<DynamicProducts> getAllDynamicProducts() {
        log.debug("REST request to get all DynamicProducts");
        return dynamicProductsRepository.findAll();
    }

    /**
     * {@code GET  /dynamic-products/:id} : get the "id" dynamicProducts.
     *
     * @param id the id of the dynamicProducts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dynamicProducts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dynamic-products/{id}")
    public ResponseEntity<DynamicProducts> getDynamicProducts(@PathVariable Long id) {
        log.debug("REST request to get DynamicProducts : {}", id);
        Optional<DynamicProducts> dynamicProducts = dynamicProductsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dynamicProducts);
    }

    /**
     * {@code DELETE  /dynamic-products/:id} : delete the "id" dynamicProducts.
     *
     * @param id the id of the dynamicProducts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dynamic-products/{id}")
    public ResponseEntity<Void> deleteDynamicProducts(@PathVariable Long id) {
        log.debug("REST request to delete DynamicProducts : {}", id);
        dynamicProductsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
