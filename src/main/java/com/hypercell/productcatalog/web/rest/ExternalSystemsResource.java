package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.ExternalSystems;
import com.hypercell.productcatalog.repository.ExternalSystemsRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.ExternalSystems}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExternalSystemsResource {

    private final Logger log = LoggerFactory.getLogger(ExternalSystemsResource.class);

    private static final String ENTITY_NAME = "productCatalogExternalSystems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExternalSystemsRepository externalSystemsRepository;

    public ExternalSystemsResource(ExternalSystemsRepository externalSystemsRepository) {
        this.externalSystemsRepository = externalSystemsRepository;
    }

    /**
     * {@code POST  /external-systems} : Create a new externalSystems.
     *
     * @param externalSystems the externalSystems to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new externalSystems, or with status {@code 400 (Bad Request)} if the externalSystems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/external-systems")
    public ResponseEntity<ExternalSystems> createExternalSystems(@RequestBody ExternalSystems externalSystems) throws URISyntaxException {
        log.debug("REST request to save ExternalSystems : {}", externalSystems);
        if (externalSystems.getId() != null) {
            throw new BadRequestAlertException("A new externalSystems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExternalSystems result = externalSystemsRepository.save(externalSystems);
        return ResponseEntity.created(new URI("/api/external-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /external-systems} : Updates an existing externalSystems.
     *
     * @param externalSystems the externalSystems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated externalSystems,
     * or with status {@code 400 (Bad Request)} if the externalSystems is not valid,
     * or with status {@code 500 (Internal Server Error)} if the externalSystems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/external-systems")
    public ResponseEntity<ExternalSystems> updateExternalSystems(@RequestBody ExternalSystems externalSystems) throws URISyntaxException {
        log.debug("REST request to update ExternalSystems : {}", externalSystems);
        if (externalSystems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExternalSystems result = externalSystemsRepository.save(externalSystems);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, externalSystems.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /external-systems} : get all the externalSystems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of externalSystems in body.
     */
    @GetMapping("/external-systems")
    public List<ExternalSystems> getAllExternalSystems() {
        log.debug("REST request to get all ExternalSystems");
        return externalSystemsRepository.findAll();
    }

    /**
     * {@code GET  /external-systems/:id} : get the "id" externalSystems.
     *
     * @param id the id of the externalSystems to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the externalSystems, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/external-systems/{id}")
    public ResponseEntity<ExternalSystems> getExternalSystems(@PathVariable Long id) {
        log.debug("REST request to get ExternalSystems : {}", id);
        Optional<ExternalSystems> externalSystems = externalSystemsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(externalSystems);
    }

    /**
     * {@code DELETE  /external-systems/:id} : delete the "id" externalSystems.
     *
     * @param id the id of the externalSystems to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/external-systems/{id}")
    public ResponseEntity<Void> deleteExternalSystems(@PathVariable Long id) {
        log.debug("REST request to delete ExternalSystems : {}", id);
        externalSystemsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
