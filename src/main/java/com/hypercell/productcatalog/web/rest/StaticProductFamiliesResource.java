package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProductFamilies;
import com.hypercell.productcatalog.repository.StaticProductFamiliesRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProductFamilies}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductFamiliesResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductFamiliesResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProductFamilies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductFamiliesRepository staticProductFamiliesRepository;

    public StaticProductFamiliesResource(StaticProductFamiliesRepository staticProductFamiliesRepository) {
        this.staticProductFamiliesRepository = staticProductFamiliesRepository;
    }

    /**
     * {@code POST  /static-product-families} : Create a new staticProductFamilies.
     *
     * @param staticProductFamilies the staticProductFamilies to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProductFamilies, or with status {@code 400 (Bad Request)} if the staticProductFamilies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-product-families")
    public ResponseEntity<StaticProductFamilies> createStaticProductFamilies(@RequestBody StaticProductFamilies staticProductFamilies) throws URISyntaxException {
        log.debug("REST request to save StaticProductFamilies : {}", staticProductFamilies);
        if (staticProductFamilies.getId() != null) {
            throw new BadRequestAlertException("A new staticProductFamilies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProductFamilies result = staticProductFamiliesRepository.save(staticProductFamilies);
        return ResponseEntity.created(new URI("/api/static-product-families/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-product-families} : Updates an existing staticProductFamilies.
     *
     * @param staticProductFamilies the staticProductFamilies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProductFamilies,
     * or with status {@code 400 (Bad Request)} if the staticProductFamilies is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProductFamilies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-product-families")
    public ResponseEntity<StaticProductFamilies> updateStaticProductFamilies(@RequestBody StaticProductFamilies staticProductFamilies) throws URISyntaxException {
        log.debug("REST request to update StaticProductFamilies : {}", staticProductFamilies);
        if (staticProductFamilies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProductFamilies result = staticProductFamiliesRepository.save(staticProductFamilies);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProductFamilies.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-product-families} : get all the staticProductFamilies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProductFamilies in body.
     */
    @GetMapping("/static-product-families")
    public List<StaticProductFamilies> getAllStaticProductFamilies() {
        log.debug("REST request to get all StaticProductFamilies");
        return staticProductFamiliesRepository.findAll();
    }

    /**
     * {@code GET  /static-product-families/:id} : get the "id" staticProductFamilies.
     *
     * @param id the id of the staticProductFamilies to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProductFamilies, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-product-families/{id}")
    public ResponseEntity<StaticProductFamilies> getStaticProductFamilies(@PathVariable Long id) {
        log.debug("REST request to get StaticProductFamilies : {}", id);
        Optional<StaticProductFamilies> staticProductFamilies = staticProductFamiliesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProductFamilies);
    }

    /**
     * {@code DELETE  /static-product-families/:id} : delete the "id" staticProductFamilies.
     *
     * @param id the id of the staticProductFamilies to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-product-families/{id}")
    public ResponseEntity<Void> deleteStaticProductFamilies(@PathVariable Long id) {
        log.debug("REST request to delete StaticProductFamilies : {}", id);
        staticProductFamiliesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
