package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProductsDetails;
import com.hypercell.productcatalog.repository.StaticProductsDetailsRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProductsDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductsDetailsResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductsDetailsResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProductsDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductsDetailsRepository staticProductsDetailsRepository;

    public StaticProductsDetailsResource(StaticProductsDetailsRepository staticProductsDetailsRepository) {
        this.staticProductsDetailsRepository = staticProductsDetailsRepository;
    }

    /**
     * {@code POST  /static-products-details} : Create a new staticProductsDetails.
     *
     * @param staticProductsDetails the staticProductsDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProductsDetails, or with status {@code 400 (Bad Request)} if the staticProductsDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-products-details")
    public ResponseEntity<StaticProductsDetails> createStaticProductsDetails(@RequestBody StaticProductsDetails staticProductsDetails) throws URISyntaxException {
        log.debug("REST request to save StaticProductsDetails : {}", staticProductsDetails);
        if (staticProductsDetails.getId() != null) {
            throw new BadRequestAlertException("A new staticProductsDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProductsDetails result = staticProductsDetailsRepository.save(staticProductsDetails);
        return ResponseEntity.created(new URI("/api/static-products-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-products-details} : Updates an existing staticProductsDetails.
     *
     * @param staticProductsDetails the staticProductsDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProductsDetails,
     * or with status {@code 400 (Bad Request)} if the staticProductsDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProductsDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-products-details")
    public ResponseEntity<StaticProductsDetails> updateStaticProductsDetails(@RequestBody StaticProductsDetails staticProductsDetails) throws URISyntaxException {
        log.debug("REST request to update StaticProductsDetails : {}", staticProductsDetails);
        if (staticProductsDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProductsDetails result = staticProductsDetailsRepository.save(staticProductsDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProductsDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-products-details} : get all the staticProductsDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProductsDetails in body.
     */
    @GetMapping("/static-products-details")
    public List<StaticProductsDetails> getAllStaticProductsDetails() {
        log.debug("REST request to get all StaticProductsDetails");
        return staticProductsDetailsRepository.findAll();
    }

    /**
     * {@code GET  /static-products-details/:id} : get the "id" staticProductsDetails.
     *
     * @param id the id of the staticProductsDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProductsDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-products-details/{id}")
    public ResponseEntity<StaticProductsDetails> getStaticProductsDetails(@PathVariable Long id) {
        log.debug("REST request to get StaticProductsDetails : {}", id);
        Optional<StaticProductsDetails> staticProductsDetails = staticProductsDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProductsDetails);
    }

    /**
     * {@code DELETE  /static-products-details/:id} : delete the "id" staticProductsDetails.
     *
     * @param id the id of the staticProductsDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-products-details/{id}")
    public ResponseEntity<Void> deleteStaticProductsDetails(@PathVariable Long id) {
        log.debug("REST request to delete StaticProductsDetails : {}", id);
        staticProductsDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
