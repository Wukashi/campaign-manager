package com.futurumtechnology.campaign_manager.repository;

import com.futurumtechnology.campaign_manager.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
