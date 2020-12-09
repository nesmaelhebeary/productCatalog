package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.ConditionDetails;
import com.hypercell.productcatalog.repository.ConditionDetailsRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.ConditionDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConditionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ConditionDetailsResource.class);

    private static final String ENTITY_NAME = "productCatalogConditionDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConditionDetailsRepository conditionDetailsRepository;

    public ConditionDetailsResource(ConditionDetailsRepository conditionDetailsRepository) {
        this.conditionDetailsRepository = conditionDetailsRepository;
    }

    /**
     * {@code POST  /condition-details} : Create a new conditionDetails.
     *
     * @param conditionDetails the conditionDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conditionDetails, or with status {@code 400 (Bad Request)} if the conditionDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/condition-details")
    public ResponseEntity<ConditionDetails> createConditionDetails(@RequestBody ConditionDetails conditionDetails) throws URISyntaxException {
        log.debug("REST request to save ConditionDetails : {}", conditionDetails);
        if (conditionDetails.getId() != null) {
            throw new BadRequestAlertException("A new conditionDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConditionDetails result = conditionDetailsRepository.save(conditionDetails);
        return ResponseEntity.created(new URI("/api/condition-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /condition-details} : Updates an existing conditionDetails.
     *
     * @param conditionDetails the conditionDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conditionDetails,
     * or with status {@code 400 (Bad Request)} if the conditionDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conditionDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/condition-details")
    public ResponseEntity<ConditionDetails> updateConditionDetails(@RequestBody ConditionDetails conditionDetails) throws URISyntaxException {
        log.debug("REST request to update ConditionDetails : {}", conditionDetails);
        if (conditionDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConditionDetails result = conditionDetailsRepository.save(conditionDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conditionDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /condition-details} : get all the conditionDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conditionDetails in body.
     */
    @GetMapping("/condition-details")
    public List<ConditionDetails> getAllConditionDetails() {
        log.debug("REST request to get all ConditionDetails");
        return conditionDetailsRepository.findAll();
    }

    /**
     * {@code GET  /condition-details/:id} : get the "id" conditionDetails.
     *
     * @param id the id of the conditionDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conditionDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/condition-details/{id}")
    public ResponseEntity<ConditionDetails> getConditionDetails(@PathVariable Long id) {
        log.debug("REST request to get ConditionDetails : {}", id);
        Optional<ConditionDetails> conditionDetails = conditionDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conditionDetails);
    }

    /**
     * {@code DELETE  /condition-details/:id} : delete the "id" conditionDetails.
     *
     * @param id the id of the conditionDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/condition-details/{id}")
    public ResponseEntity<Void> deleteConditionDetails(@PathVariable Long id) {
        log.debug("REST request to delete ConditionDetails : {}", id);
        conditionDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
