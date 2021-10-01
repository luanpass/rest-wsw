package com.wsw.rest.http;

import com.wsw.rest.data.ProductData;
import com.wsw.rest.model.ProductModel;
import com.wsw.rest.usecase.ProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductUseCase useCase;

    @GetMapping
    public ResponseEntity<List<ProductModel>> products() {
        return useCase.getProductAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductModel> product(@PathVariable Long id) {
        return useCase.getProductById(id);
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/{id}")
    public ResponseEntity productHead(@PathVariable Long id) {
        return useCase.existsProductById(id);
    }

    @PostMapping
    private ResponseEntity<ProductModel> saveProduct(@RequestBody ProductData productData) {
        return useCase.saveProduct(productData);
    }

    @PutMapping(path = "/{id}")
    private ResponseEntity updateProduct(@PathVariable Long id, @RequestBody ProductData productData) {
        return useCase.updateProduct(id, productData);
    }


    @DeleteMapping(path = "/{id}")
    private ResponseEntity deleteProduct(@PathVariable Long id) {
        return useCase.deleteProduct(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    ResponseEntity<?> productOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.OPTIONS, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS)
                .build();
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> productsOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.HEAD, HttpMethod.OPTIONS)
                .build();
    }
}
