package com.wsw.rest.usecase;

import com.wsw.rest.converter.ProductConverter;
import com.wsw.rest.data.ProductData;
import com.wsw.rest.http.ProductController;
import com.wsw.rest.model.ProductModel;
import com.wsw.rest.repository.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductUseCase {
    @Resource
    ProductConverter converter;
    @Resource
    ProductRepository repository;

    public ResponseEntity<List<ProductModel>> getProductAll() {
        List<ProductModel> productList = repository.findAll();
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (ProductModel productModel : productList) {
                long id = productModel.getId();
                productModel.add(linkTo(methodOn(ProductController.class).product(id)).withSelfRel());
            }
            return new ResponseEntity<>(productList, HttpStatus.OK);
        }
    }

    public ResponseEntity<ProductModel> getProductById(Long id) {
        Optional<ProductModel> product = repository.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            product.get().add(linkTo(methodOn(ProductController.class).products()).withRel("Lista com todos os produtos"));
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity existsProductById(Long id) {
        boolean exists = repository.existsById(id);
        return (exists ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<ProductModel> saveProduct(ProductData productData) {
        try {
            ProductModel productModel = repository.save(converter.toModel(productData));
            return new ResponseEntity<>(productModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity updateProduct(Long id, ProductData productData) {
        productData.setId(id);
        boolean exists = repository.existsById(id);
        return (exists ? new ResponseEntity<>(repository.save(converter.toModel(productData)), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    public ResponseEntity deleteProduct(Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
