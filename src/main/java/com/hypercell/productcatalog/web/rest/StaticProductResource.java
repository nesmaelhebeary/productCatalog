package com.hypercell.productcatalog.web.rest;

import com.hypercell.productcatalog.domain.StaticProduct;
import com.hypercell.productcatalog.repository.StaticProductRepository;
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
 * REST controller for managing {@link com.hypercell.productcatalog.domain.StaticProduct}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaticProductResource {

    private final Logger log = LoggerFactory.getLogger(StaticProductResource.class);

    private static final String ENTITY_NAME = "productCatalogStaticProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticProductRepository staticProductRepository;

    public StaticProductResource(StaticProductRepository staticProductRepository) {
        this.staticProductRepository = staticProductRepository;
    }

    /**
     * {@code POST  /static-products} : Create a new staticProduct.
     *
     * @param staticProduct the staticProduct to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticProduct, or with status {@code 400 (Bad Request)} if the staticProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-products")
    public ResponseEntity<StaticProduct> createStaticProduct(@RequestBody StaticProduct staticProduct) throws URISyntaxException {
        log.debug("REST request to save StaticProduct : {}", staticProduct);
        if (staticProduct.getId() != null) {
            throw new BadRequestAlertException("A new staticProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticProduct result = staticProductRepository.save(staticProduct);
        return ResponseEntity.created(new URI("/api/static-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-products} : Updates an existing staticProduct.
     *
     * @param staticProduct the staticProduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticProduct,
     * or with status {@code 400 (Bad Request)} if the staticProduct is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticProduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-products")
    public ResponseEntity<StaticProduct> updateStaticProduct(@RequestBody StaticProduct staticProduct) throws URISyntaxException {
        log.debug("REST request to update StaticProduct : {}", staticProduct);
        if (staticProduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticProduct result = staticProductRepository.save(staticProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticProduct.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-products} : get all the staticProducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticProducts in body.
     */
    @GetMapping("/static-products")
    public List<StaticProduct> getAllStaticProducts() {
        log.debug("REST request to get all StaticProducts");
        return staticProductRepository.findAll();
    }

    /**
     * {@code GET  /static-products/:id} : get the "id" staticProduct.
     *
     * @param id the id of the staticProduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticProduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-products/{id}")
    public ResponseEntity<StaticProduct> getStaticProduct(@PathVariable Long id) {
        log.debug("REST request to get StaticProduct : {}", id);
        Optional<StaticProduct> staticProduct = staticProductRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticProduct);
    }

    /**
     * {@code DELETE  /static-products/:id} : delete the "id" staticProduct.
     *
     * @param id the id of the staticProduct to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-products/{id}")
    public ResponseEntity<Void> deleteStaticProduct(@PathVariable Long id) {
        log.debug("REST request to delete StaticProduct : {}", id);
        staticProductRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
