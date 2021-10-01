package com.wsw.rest.converter;

import com.wsw.rest.data.ProductData;
import com.wsw.rest.model.ProductModel;

public interface ProductConverter {

    ProductModel toModel(ProductData productData);
}
