package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProductFamilyEligibiltyRules;
import com.hypercell.productcatalog.repository.StaticProductFamilyEligibiltyRulesRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProductFamilyEligibiltyRules}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductFamilyEligibiltyRulesResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductFamilyEligibiltyRulesResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProductFamilyEligibiltyRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductFamilyEligibiltyRulesRepository staticProductFamilyEligibiltyRulesRepository;

    public StaticProductFamilyEligibiltyRulesResource(StaticProductFamilyEligibiltyRulesRepository staticProductFamilyEligibiltyRulesRepository) {
        this.staticProductFamilyEligibiltyRulesRepository = staticProductFamilyEligibiltyRulesRepository;
    }

    /**
     * {@code POST  /static-product-family-eligibilty-rules} : Create a new staticProductFamilyEligibiltyRules.
     *
     * @param staticProductFamilyEligibiltyRules the staticProductFamilyEligibiltyRules to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProductFamilyEligibiltyRules, or with status {@code 400 (Bad Request)} if the staticProductFamilyEligibiltyRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-product-family-eligibilty-rules")
    public ResponseEntity<StaticProductFamilyEligibiltyRules> createStaticProductFamilyEligibiltyRules(@RequestBody StaticProductFamilyEligibiltyRules staticProductFamilyEligibiltyRules) throws URISyntaxException {
        log.debug("REST request to save StaticProductFamilyEligibiltyRules : {}", staticProductFamilyEligibiltyRules);
        if (staticProductFamilyEligibiltyRules.getId() != null) {
            throw new BadRequestAlertException("A new staticProductFamilyEligibiltyRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProductFamilyEligibiltyRules result = staticProductFamilyEligibiltyRulesRepository.save(staticProductFamilyEligibiltyRules);
        return ResponseEntity.created(new URI("/api/static-product-family-eligibilty-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-product-family-eligibilty-rules} : Updates an existing staticProductFamilyEligibiltyRules.
     *
     * @param staticProductFamilyEligibiltyRules the staticProductFamilyEligibiltyRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProductFamilyEligibiltyRules,
     * or with status {@code 400 (Bad Request)} if the staticProductFamilyEligibiltyRules is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProductFamilyEligibiltyRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-product-family-eligibilty-rules")
    public ResponseEntity<StaticProductFamilyEligibiltyRules> updateStaticProductFamilyEligibiltyRules(@RequestBody StaticProductFamilyEligibiltyRules staticProductFamilyEligibiltyRules) throws URISyntaxException {
        log.debug("REST request to update StaticProductFamilyEligibiltyRules : {}", staticProductFamilyEligibiltyRules);
        if (staticProductFamilyEligibiltyRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProductFamilyEligibiltyRules result = staticProductFamilyEligibiltyRulesRepository.save(staticProductFamilyEligibiltyRules);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProductFamilyEligibiltyRules.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-product-family-eligibilty-rules} : get all the staticProductFamilyEligibiltyRules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProductFamilyEligibiltyRules in body.
     */
    @GetMapping("/static-product-family-eligibilty-rules")
    public List<StaticProductFamilyEligibiltyRules> getAllStaticProductFamilyEligibiltyRules() {
        log.debug("REST request to get all StaticProductFamilyEligibiltyRules");
        return staticProductFamilyEligibiltyRulesRepository.findAll();
    }

    /**
     * {@code GET  /static-product-family-eligibilty-rules/:id} : get the "id" staticProductFamilyEligibiltyRules.
     *
     * @param id the id of the staticProductFamilyEligibiltyRules to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProductFamilyEligibiltyRules, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-product-family-eligibilty-rules/{id}")
    public ResponseEntity<StaticProductFamilyEligibiltyRules> getStaticProductFamilyEligibiltyRules(@PathVariable Long id) {
        log.debug("REST request to get StaticProductFamilyEligibiltyRules : {}", id);
        Optional<StaticProductFamilyEligibiltyRules> staticProductFamilyEligibiltyRules = staticProductFamilyEligibiltyRulesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProductFamilyEligibiltyRules);
    }

    /**
     * {@code DELETE  /static-product-family-eligibilty-rules/:id} : delete the "id" staticProductFamilyEligibiltyRules.
     *
     * @param id the id of the staticProductFamilyEligibiltyRules to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-product-family-eligibilty-rules/{id}")
    public ResponseEntity<Void> deleteStaticProductFamilyEligibiltyRules(@PathVariable Long id) {
        log.debug("REST request to delete StaticProductFamilyEligibiltyRules : {}", id);
        staticProductFamilyEligibiltyRulesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
