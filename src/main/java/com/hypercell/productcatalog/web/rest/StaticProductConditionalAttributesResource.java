package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProductConditionalAttributes;
import com.hypercell.productcatalog.repository.StaticProductConditionalAttributesRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProductConditionalAttributes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductConditionalAttributesResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductConditionalAttributesResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProductConditionalAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductConditionalAttributesRepository staticProductConditionalAttributesRepository;

    public StaticProductConditionalAttributesResource(StaticProductConditionalAttributesRepository staticProductConditionalAttributesRepository) {
        this.staticProductConditionalAttributesRepository = staticProductConditionalAttributesRepository;
    }

    /**
     * {@code POST  /static-product-conditional-attributes} : Create a new staticProductConditionalAttributes.
     *
     * @param staticProductConditionalAttributes the staticProductConditionalAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProductConditionalAttributes, or with status {@code 400 (Bad Request)} if the staticProductConditionalAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-product-conditional-attributes")
    public ResponseEntity<StaticProductConditionalAttributes> createStaticProductConditionalAttributes(@RequestBody StaticProductConditionalAttributes staticProductConditionalAttributes) throws URISyntaxException {
        log.debug("REST request to save StaticProductConditionalAttributes : {}", staticProductConditionalAttributes);
        if (staticProductConditionalAttributes.getId() != null) {
            throw new BadRequestAlertException("A new staticProductConditionalAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProductConditionalAttributes result = staticProductConditionalAttributesRepository.save(staticProductConditionalAttributes);
        return ResponseEntity.created(new URI("/api/static-product-conditional-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-product-conditional-attributes} : Updates an existing staticProductConditionalAttributes.
     *
     * @param staticProductConditionalAttributes the staticProductConditionalAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProductConditionalAttributes,
     * or with status {@code 400 (Bad Request)} if the staticProductConditionalAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProductConditionalAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-product-conditional-attributes")
    public ResponseEntity<StaticProductConditionalAttributes> updateStaticProductConditionalAttributes(@RequestBody StaticProductConditionalAttributes staticProductConditionalAttributes) throws URISyntaxException {
        log.debug("REST request to update StaticProductConditionalAttributes : {}", staticProductConditionalAttributes);
        if (staticProductConditionalAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProductConditionalAttributes result = staticProductConditionalAttributesRepository.save(staticProductConditionalAttributes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProductConditionalAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-product-conditional-attributes} : get all the staticProductConditionalAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProductConditionalAttributes in body.
     */
    @GetMapping("/static-product-conditional-attributes")
    public List<StaticProductConditionalAttributes> getAllStaticProductConditionalAttributes() {
        log.debug("REST request to get all StaticProductConditionalAttributes");
        return staticProductConditionalAttributesRepository.findAll();
    }

    /**
     * {@code GET  /static-product-conditional-attributes/:id} : get the "id" staticProductConditionalAttributes.
     *
     * @param id the id of the staticProductConditionalAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProductConditionalAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-product-conditional-attributes/{id}")
    public ResponseEntity<StaticProductConditionalAttributes> getStaticProductConditionalAttributes(@PathVariable Long id) {
        log.debug("REST request to get StaticProductConditionalAttributes : {}", id);
        Optional<StaticProductConditionalAttributes> staticProductConditionalAttributes = staticProductConditionalAttributesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProductConditionalAttributes);
    }

    /**
     * {@code DELETE  /static-product-conditional-attributes/:id} : delete the "id" staticProductConditionalAttributes.
     *
     * @param id the id of the staticProductConditionalAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-product-conditional-attributes/{id}")
    public ResponseEntity<Void> deleteStaticProductConditionalAttributes(@PathVariable Long id) {
        log.debug("REST request to delete StaticProductConditionalAttributes : {}", id);
        staticProductConditionalAttributesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
