package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProductBasicAttributes;
import com.hypercell.productcatalog.repository.StaticProductBasicAttributesRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProductBasicAttributes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductBasicAttributesResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductBasicAttributesResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProductBasicAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductBasicAttributesRepository staticProductBasicAttributesRepository;

    public StaticProductBasicAttributesResource(StaticProductBasicAttributesRepository staticProductBasicAttributesRepository) {
        this.staticProductBasicAttributesRepository = staticProductBasicAttributesRepository;
    }

    /**
     * {@code POST  /static-product-basic-attributes} : Create a new staticProductBasicAttributes.
     *
     * @param staticProductBasicAttributes the staticProductBasicAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProductBasicAttributes, or with status {@code 400 (Bad Request)} if the staticProductBasicAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-product-basic-attributes")
    public ResponseEntity<StaticProductBasicAttributes> createStaticProductBasicAttributes(@RequestBody StaticProductBasicAttributes staticProductBasicAttributes) throws URISyntaxException {
        log.debug("REST request to save StaticProductBasicAttributes : {}", staticProductBasicAttributes);
        if (staticProductBasicAttributes.getId() != null) {
            throw new BadRequestAlertException("A new staticProductBasicAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProductBasicAttributes result = staticProductBasicAttributesRepository.save(staticProductBasicAttributes);
        return ResponseEntity.created(new URI("/api/static-product-basic-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-product-basic-attributes} : Updates an existing staticProductBasicAttributes.
     *
     * @param staticProductBasicAttributes the staticProductBasicAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProductBasicAttributes,
     * or with status {@code 400 (Bad Request)} if the staticProductBasicAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProductBasicAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-product-basic-attributes")
    public ResponseEntity<StaticProductBasicAttributes> updateStaticProductBasicAttributes(@RequestBody StaticProductBasicAttributes staticProductBasicAttributes) throws URISyntaxException {
        log.debug("REST request to update StaticProductBasicAttributes : {}", staticProductBasicAttributes);
        if (staticProductBasicAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProductBasicAttributes result = staticProductBasicAttributesRepository.save(staticProductBasicAttributes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProductBasicAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-product-basic-attributes} : get all the staticProductBasicAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProductBasicAttributes in body.
     */
    @GetMapping("/static-product-basic-attributes")
    public List<StaticProductBasicAttributes> getAllStaticProductBasicAttributes() {
        log.debug("REST request to get all StaticProductBasicAttributes");
        return staticProductBasicAttributesRepository.findAll();
    }

    /**
     * {@code GET  /static-product-basic-attributes/:id} : get the "id" staticProductBasicAttributes.
     *
     * @param id the id of the staticProductBasicAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProductBasicAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-product-basic-attributes/{id}")
    public ResponseEntity<StaticProductBasicAttributes> getStaticProductBasicAttributes(@PathVariable Long id) {
        log.debug("REST request to get StaticProductBasicAttributes : {}", id);
        Optional<StaticProductBasicAttributes> staticProductBasicAttributes = staticProductBasicAttributesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProductBasicAttributes);
    }

    /**
     * {@code DELETE  /static-product-basic-attributes/:id} : delete the "id" staticProductBasicAttributes.
     *
     * @param id the id of the staticProductBasicAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-product-basic-attributes/{id}")
    public ResponseEntity<Void> deleteStaticProductBasicAttributes(@PathVariable Long id) {
        log.debug("REST request to delete StaticProductBasicAttributes : {}", id);
        staticProductBasicAttributesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
