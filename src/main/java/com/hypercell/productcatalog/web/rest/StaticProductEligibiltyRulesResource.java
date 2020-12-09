package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProductEligibiltyRules;
import com.hypercell.productcatalog.repository.StaticProductEligibiltyRulesRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProductEligibiltyRules}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductEligibiltyRulesResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductEligibiltyRulesResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProductEligibiltyRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductEligibiltyRulesRepository staticProductEligibiltyRulesRepository;

    public StaticProductEligibiltyRulesResource(StaticProductEligibiltyRulesRepository staticProductEligibiltyRulesRepository) {
        this.staticProductEligibiltyRulesRepository = staticProductEligibiltyRulesRepository;
    }

    /**
     * {@code POST  /static-product-eligibilty-rules} : Create a new staticProductEligibiltyRules.
     *
     * @param staticProductEligibiltyRules the staticProductEligibiltyRules to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProductEligibiltyRules, or with status {@code 400 (Bad Request)} if the staticProductEligibiltyRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-product-eligibilty-rules")
    public ResponseEntity<StaticProductEligibiltyRules> createStaticProductEligibiltyRules(@RequestBody StaticProductEligibiltyRules staticProductEligibiltyRules) throws URISyntaxException {
        log.debug("REST request to save StaticProductEligibiltyRules : {}", staticProductEligibiltyRules);
        if (staticProductEligibiltyRules.getId() != null) {
            throw new BadRequestAlertException("A new staticProductEligibiltyRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProductEligibiltyRules result = staticProductEligibiltyRulesRepository.save(staticProductEligibiltyRules);
        return ResponseEntity.created(new URI("/api/static-product-eligibilty-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-product-eligibilty-rules} : Updates an existing staticProductEligibiltyRules.
     *
     * @param staticProductEligibiltyRules the staticProductEligibiltyRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProductEligibiltyRules,
     * or with status {@code 400 (Bad Request)} if the staticProductEligibiltyRules is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProductEligibiltyRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-product-eligibilty-rules")
    public ResponseEntity<StaticProductEligibiltyRules> updateStaticProductEligibiltyRules(@RequestBody StaticProductEligibiltyRules staticProductEligibiltyRules) throws URISyntaxException {
        log.debug("REST request to update StaticProductEligibiltyRules : {}", staticProductEligibiltyRules);
        if (staticProductEligibiltyRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProductEligibiltyRules result = staticProductEligibiltyRulesRepository.save(staticProductEligibiltyRules);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProductEligibiltyRules.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-product-eligibilty-rules} : get all the staticProductEligibiltyRules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProductEligibiltyRules in body.
     */
    @GetMapping("/static-product-eligibilty-rules")
    public List<StaticProductEligibiltyRules> getAllStaticProductEligibiltyRules() {
        log.debug("REST request to get all StaticProductEligibiltyRules");
        return staticProductEligibiltyRulesRepository.findAll();
    }

    /**
     * {@code GET  /static-product-eligibilty-rules/:id} : get the "id" staticProductEligibiltyRules.
     *
     * @param id the id of the staticProductEligibiltyRules to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProductEligibiltyRules, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-product-eligibilty-rules/{id}")
    public ResponseEntity<StaticProductEligibiltyRules> getStaticProductEligibiltyRules(@PathVariable Long id) {
        log.debug("REST request to get StaticProductEligibiltyRules : {}", id);
        Optional<StaticProductEligibiltyRules> staticProductEligibiltyRules = staticProductEligibiltyRulesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProductEligibiltyRules);
    }

    /**
     * {@code DELETE  /static-product-eligibilty-rules/:id} : delete the "id" staticProductEligibiltyRules.
     *
     * @param id the id of the staticProductEligibiltyRules to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-product-eligibilty-rules/{id}")
    public ResponseEntity<Void> deleteStaticProductEligibiltyRules(@PathVariable Long id) {
        log.debug("REST request to delete StaticProductEligibiltyRules : {}", id);
        staticProductEligibiltyRulesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
