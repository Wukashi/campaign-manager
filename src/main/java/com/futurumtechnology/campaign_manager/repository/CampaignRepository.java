package com.futurumtechnology.campaign_manager.repository;

import com.futurumtechnology.campaign_manager.entities.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByProductId(Long productId);
}
