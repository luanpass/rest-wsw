package com.wsw.rest.repository;

import com.wsw.rest.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    @Override
    <S extends ProductModel> S save(S entity);

    @Override
    Optional<ProductModel> findById(Long aLong);

    @Override
    List<ProductModel> findAll();

    @Override
    void deleteById(Long aLong);

    @Override
    boolean existsById(Long aLong);
}
