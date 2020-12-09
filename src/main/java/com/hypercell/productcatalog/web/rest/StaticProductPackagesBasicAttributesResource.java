package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProductPackagesBasicAttributes;
import com.hypercell.productcatalog.repository.StaticProductPackagesBasicAttributesRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProductPackagesBasicAttributes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductPackagesBasicAttributesResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductPackagesBasicAttributesResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProductPackagesBasicAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductPackagesBasicAttributesRepository staticProductPackagesBasicAttributesRepository;

    public StaticProductPackagesBasicAttributesResource(StaticProductPackagesBasicAttributesRepository staticProductPackagesBasicAttributesRepository) {
        this.staticProductPackagesBasicAttributesRepository = staticProductPackagesBasicAttributesRepository;
    }

    /**
     * {@code POST  /static-product-packages-basic-attributes} : Create a new staticProductPackagesBasicAttributes.
     *
     * @param staticProductPackagesBasicAttributes the staticProductPackagesBasicAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProductPackagesBasicAttributes, or with status {@code 400 (Bad Request)} if the staticProductPackagesBasicAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-product-packages-basic-attributes")
    public ResponseEntity<StaticProductPackagesBasicAttributes> createStaticProductPackagesBasicAttributes(@RequestBody StaticProductPackagesBasicAttributes staticProductPackagesBasicAttributes) throws URISyntaxException {
        log.debug("REST request to save StaticProductPackagesBasicAttributes : {}", staticProductPackagesBasicAttributes);
        if (staticProductPackagesBasicAttributes.getId() != null) {
            throw new BadRequestAlertException("A new staticProductPackagesBasicAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProductPackagesBasicAttributes result = staticProductPackagesBasicAttributesRepository.save(staticProductPackagesBasicAttributes);
        return ResponseEntity.created(new URI("/api/static-product-packages-basic-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-product-packages-basic-attributes} : Updates an existing staticProductPackagesBasicAttributes.
     *
     * @param staticProductPackagesBasicAttributes the staticProductPackagesBasicAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProductPackagesBasicAttributes,
     * or with status {@code 400 (Bad Request)} if the staticProductPackagesBasicAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProductPackagesBasicAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-product-packages-basic-attributes")
    public ResponseEntity<StaticProductPackagesBasicAttributes> updateStaticProductPackagesBasicAttributes(@RequestBody StaticProductPackagesBasicAttributes staticProductPackagesBasicAttributes) throws URISyntaxException {
        log.debug("REST request to update StaticProductPackagesBasicAttributes : {}", staticProductPackagesBasicAttributes);
        if (staticProductPackagesBasicAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProductPackagesBasicAttributes result = staticProductPackagesBasicAttributesRepository.save(staticProductPackagesBasicAttributes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProductPackagesBasicAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-product-packages-basic-attributes} : get all the staticProductPackagesBasicAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProductPackagesBasicAttributes in body.
     */
    @GetMapping("/static-product-packages-basic-attributes")
    public List<StaticProductPackagesBasicAttributes> getAllStaticProductPackagesBasicAttributes() {
        log.debug("REST request to get all StaticProductPackagesBasicAttributes");
        return staticProductPackagesBasicAttributesRepository.findAll();
    }

    /**
     * {@code GET  /static-product-packages-basic-attributes/:id} : get the "id" staticProductPackagesBasicAttributes.
     *
     * @param id the id of the staticProductPackagesBasicAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProductPackagesBasicAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-product-packages-basic-attributes/{id}")
    public ResponseEntity<StaticProductPackagesBasicAttributes> getStaticProductPackagesBasicAttributes(@PathVariable Long id) {
        log.debug("REST request to get StaticProductPackagesBasicAttributes : {}", id);
        Optional<StaticProductPackagesBasicAttributes> staticProductPackagesBasicAttributes = staticProductPackagesBasicAttributesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProductPackagesBasicAttributes);
    }

    /**
     * {@code DELETE  /static-product-packages-basic-attributes/:id} : delete the "id" staticProductPackagesBasicAttributes.
     *
     * @param id the id of the staticProductPackagesBasicAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-product-packages-basic-attributes/{id}")
    public ResponseEntity<Void> deleteStaticProductPackagesBasicAttributes(@PathVariable Long id) {
        log.debug("REST request to delete StaticProductPackagesBasicAttributes : {}", id);
        staticProductPackagesBasicAttributesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
