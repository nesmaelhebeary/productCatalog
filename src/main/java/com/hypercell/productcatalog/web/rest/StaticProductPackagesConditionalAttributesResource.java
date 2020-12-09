package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProductPackagesConditionalAttributes;
import com.hypercell.productcatalog.repository.StaticProductPackagesConditionalAttributesRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProductPackagesConditionalAttributes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductPackagesConditionalAttributesResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductPackagesConditionalAttributesResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProductPackagesConditionalAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductPackagesConditionalAttributesRepository staticProductPackagesConditionalAttributesRepository;

    public StaticProductPackagesConditionalAttributesResource(StaticProductPackagesConditionalAttributesRepository staticProductPackagesConditionalAttributesRepository) {
        this.staticProductPackagesConditionalAttributesRepository = staticProductPackagesConditionalAttributesRepository;
    }

    /**
     * {@code POST  /static-product-packages-conditional-attributes} : Create a new staticProductPackagesConditionalAttributes.
     *
     * @param staticProductPackagesConditionalAttributes the staticProductPackagesConditionalAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProductPackagesConditionalAttributes, or with status {@code 400 (Bad Request)} if the staticProductPackagesConditionalAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-product-packages-conditional-attributes")
    public ResponseEntity<StaticProductPackagesConditionalAttributes> createStaticProductPackagesConditionalAttributes(@RequestBody StaticProductPackagesConditionalAttributes staticProductPackagesConditionalAttributes) throws URISyntaxException {
        log.debug("REST request to save StaticProductPackagesConditionalAttributes : {}", staticProductPackagesConditionalAttributes);
        if (staticProductPackagesConditionalAttributes.getId() != null) {
            throw new BadRequestAlertException("A new staticProductPackagesConditionalAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProductPackagesConditionalAttributes result = staticProductPackagesConditionalAttributesRepository.save(staticProductPackagesConditionalAttributes);
        return ResponseEntity.created(new URI("/api/static-product-packages-conditional-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-product-packages-conditional-attributes} : Updates an existing staticProductPackagesConditionalAttributes.
     *
     * @param staticProductPackagesConditionalAttributes the staticProductPackagesConditionalAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProductPackagesConditionalAttributes,
     * or with status {@code 400 (Bad Request)} if the staticProductPackagesConditionalAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProductPackagesConditionalAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-product-packages-conditional-attributes")
    public ResponseEntity<StaticProductPackagesConditionalAttributes> updateStaticProductPackagesConditionalAttributes(@RequestBody StaticProductPackagesConditionalAttributes staticProductPackagesConditionalAttributes) throws URISyntaxException {
        log.debug("REST request to update StaticProductPackagesConditionalAttributes : {}", staticProductPackagesConditionalAttributes);
        if (staticProductPackagesConditionalAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProductPackagesConditionalAttributes result = staticProductPackagesConditionalAttributesRepository.save(staticProductPackagesConditionalAttributes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProductPackagesConditionalAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-product-packages-conditional-attributes} : get all the staticProductPackagesConditionalAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProductPackagesConditionalAttributes in body.
     */
    @GetMapping("/static-product-packages-conditional-attributes")
    public List<StaticProductPackagesConditionalAttributes> getAllStaticProductPackagesConditionalAttributes() {
        log.debug("REST request to get all StaticProductPackagesConditionalAttributes");
        return staticProductPackagesConditionalAttributesRepository.findAll();
    }

    /**
     * {@code GET  /static-product-packages-conditional-attributes/:id} : get the "id" staticProductPackagesConditionalAttributes.
     *
     * @param id the id of the staticProductPackagesConditionalAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProductPackagesConditionalAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-product-packages-conditional-attributes/{id}")
    public ResponseEntity<StaticProductPackagesConditionalAttributes> getStaticProductPackagesConditionalAttributes(@PathVariable Long id) {
        log.debug("REST request to get StaticProductPackagesConditionalAttributes : {}", id);
        Optional<StaticProductPackagesConditionalAttributes> staticProductPackagesConditionalAttributes = staticProductPackagesConditionalAttributesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProductPackagesConditionalAttributes);
    }

    /**
     * {@code DELETE  /static-product-packages-conditional-attributes/:id} : delete the "id" staticProductPackagesConditionalAttributes.
     *
     * @param id the id of the staticProductPackagesConditionalAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-product-packages-conditional-attributes/{id}")
    public ResponseEntity<Void> deleteStaticProductPackagesConditionalAttributes(@PathVariable Long id) {
        log.debug("REST request to delete StaticProductPackagesConditionalAttributes : {}", id);
        staticProductPackagesConditionalAttributesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
