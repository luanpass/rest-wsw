package com.wsw.rest.converter.impl;

import com.wsw.rest.converter.ProductConverter;
import com.wsw.rest.data.ProductData;
import com.wsw.rest.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
public class ProductConverterImpl implements ProductConverter {

    @Override
    public ProductModel toModel(ProductData productData) {
        return new ProductModel(productData.getId(), productData.getName(), productData.getPrice());
    }
}
