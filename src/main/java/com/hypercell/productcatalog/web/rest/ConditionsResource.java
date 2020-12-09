package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.Conditions;
import com.hypercell.productcatalog.repository.ConditionsRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.Conditions}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConditionsResource {

    private final Logger log = LoggerFactory.getLogger(ConditionsResource.class);

    private static final String ENTITY_NAME = "productCatalogConditions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConditionsRepository conditionsRepository;

    public ConditionsResource(ConditionsRepository conditionsRepository) {
        this.conditionsRepository = conditionsRepository;
    }

    /**
     * {@code POST  /conditions} : Create a new conditions.
     *
     * @param conditions the conditions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conditions, or with status {@code 400 (Bad Request)} if the conditions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conditions")
    public ResponseEntity<Conditions> createConditions(@RequestBody Conditions conditions) throws URISyntaxException {
        log.debug("REST request to save Conditions : {}", conditions);
        if (conditions.getId() != null) {
            throw new BadRequestAlertException("A new conditions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conditions result = conditionsRepository.save(conditions);
        return ResponseEntity.created(new URI("/api/conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conditions} : Updates an existing conditions.
     *
     * @param conditions the conditions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conditions,
     * or with status {@code 400 (Bad Request)} if the conditions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conditions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conditions")
    public ResponseEntity<Conditions> updateConditions(@RequestBody Conditions conditions) throws URISyntaxException {
        log.debug("REST request to update Conditions : {}", conditions);
        if (conditions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Conditions result = conditionsRepository.save(conditions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conditions.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conditions} : get all the conditions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conditions in body.
     */
    @GetMapping("/conditions")
    public List<Conditions> getAllConditions() {
        log.debug("REST request to get all Conditions");
        return conditionsRepository.findAll();
    }

    /**
     * {@code GET  /conditions/:id} : get the "id" conditions.
     *
     * @param id the id of the conditions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conditions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conditions/{id}")
    public ResponseEntity<Conditions> getConditions(@PathVariable Long id) {
        log.debug("REST request to get Conditions : {}", id);
        Optional<Conditions> conditions = conditionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conditions);
    }

    /**
     * {@code DELETE  /conditions/:id} : delete the "id" conditions.
     *
     * @param id the id of the conditions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conditions/{id}")
    public ResponseEntity<Void> deleteConditions(@PathVariable Long id) {
        log.debug("REST request to delete Conditions : {}", id);
        conditionsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
