package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProductPackages;
import com.hypercell.productcatalog.repository.StaticProductPackagesRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProductPackages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductPackagesResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductPackagesResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProductPackages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductPackagesRepository staticProductPackagesRepository;

    public StaticProductPackagesResource(StaticProductPackagesRepository staticProductPackagesRepository) {
        this.staticProductPackagesRepository = staticProductPackagesRepository;
    }

    /**
     * {@code POST  /static-product-packages} : Create a new staticProductPackages.
     *
     * @param staticProductPackages the staticProductPackages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProductPackages, or with status {@code 400 (Bad Request)} if the staticProductPackages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-product-packages")
    public ResponseEntity<StaticProductPackages> createStaticProductPackages(@RequestBody StaticProductPackages staticProductPackages) throws URISyntaxException {
        log.debug("REST request to save StaticProductPackages : {}", staticProductPackages);
        if (staticProductPackages.getId() != null) {
            throw new BadRequestAlertException("A new staticProductPackages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProductPackages result = staticProductPackagesRepository.save(staticProductPackages);
        return ResponseEntity.created(new URI("/api/static-product-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-product-packages} : Updates an existing staticProductPackages.
     *
     * @param staticProductPackages the staticProductPackages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProductPackages,
     * or with status {@code 400 (Bad Request)} if the staticProductPackages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProductPackages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-product-packages")
    public ResponseEntity<StaticProductPackages> updateStaticProductPackages(@RequestBody StaticProductPackages staticProductPackages) throws URISyntaxException {
        log.debug("REST request to update StaticProductPackages : {}", staticProductPackages);
        if (staticProductPackages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProductPackages result = staticProductPackagesRepository.save(staticProductPackages);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProductPackages.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-product-packages} : get all the staticProductPackages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProductPackages in body.
     */
    @GetMapping("/static-product-packages")
    public List<StaticProductPackages> getAllStaticProductPackages() {
        log.debug("REST request to get all StaticProductPackages");
        return staticProductPackagesRepository.findAll();
    }

    /**
     * {@code GET  /static-product-packages/:id} : get the "id" staticProductPackages.
     *
     * @param id the id of the staticProductPackages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProductPackages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-product-packages/{id}")
    public ResponseEntity<StaticProductPackages> getStaticProductPackages(@PathVariable Long id) {
        log.debug("REST request to get StaticProductPackages : {}", id);
        Optional<StaticProductPackages> staticProductPackages = staticProductPackagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProductPackages);
    }

    /**
     * {@code DELETE  /static-product-packages/:id} : delete the "id" staticProductPackages.
     *
     * @param id the id of the staticProductPackages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-product-packages/{id}")
    public ResponseEntity<Void> deleteStaticProductPackages(@PathVariable Long id) {
        log.debug("REST request to delete StaticProductPackages : {}", id);
        staticProductPackagesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
