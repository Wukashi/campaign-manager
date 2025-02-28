package com.futurumtechnology.campaign_manager.service;

import com.futurumtechnology.campaign_manager.dto.ProductDto;
import com.futurumtechnology.campaign_manager.entities.Product;
import com.futurumtechnology.campaign_manager.exceptions.AppException;
import com.futurumtechnology.campaign_manager.mapper.ProductMapper;
import com.futurumtechnology.campaign_manager.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> allProducts() {
        List<Product> all = productRepository.findAll();
        return productMapper.toProductDtoList(all);
    }

    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException("Product not found", HttpStatus.NOT_FOUND));
        return productMapper.toProductDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        Product createdProduct = productRepository.save(product);
        return productMapper.toProductDto(createdProduct);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new AppException("Product not found", HttpStatus.NOT_FOUND));

        existingProduct.setName(productDto.getName());
        existingProduct.setCategory(productDto.getCategory());

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toProductDto(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new AppException("Product not found", HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
    }
}
