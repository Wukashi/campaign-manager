package com.futurumtechnology.campaign_manager.mapper;

import com.futurumtechnology.campaign_manager.dto.ProductDto;
import com.futurumtechnology.campaign_manager.entities.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product product);

    List<Product> toProductList(List<ProductDto> producttoList);

    List<ProductDto> toProductDtoList(List<Product> productList);

}
