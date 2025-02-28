package com.futurumtechnology.campaign_manager.service;

import com.futurumtechnology.campaign_manager.dto.CampaignDto;
import com.futurumtechnology.campaign_manager.entities.Campaign;
import com.futurumtechnology.campaign_manager.entities.Product;
import com.futurumtechnology.campaign_manager.mapper.CampaignMapper;
import com.futurumtechnology.campaign_manager.repository.CampaignRepository;
import com.futurumtechnology.campaign_manager.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final ProductRepository productRepository;
    private final CampaignMapper campaignMapper;
    private final AccountService accountService;

    private static final BigDecimal MIN_BID_AMOUNT = BigDecimal.valueOf(0.5);

    public CampaignService(
            CampaignRepository campaignRepository,
            ProductRepository productRepository,
            CampaignMapper campaignMapper,
            AccountService accountService) {
        this.campaignRepository = campaignRepository;
        this.productRepository = productRepository;
        this.campaignMapper = campaignMapper;
        this.accountService = accountService;
    }

    public List<CampaignDto> getAllCampaigns() {
        return campaignRepository.findAll().stream()
                .map(campaignMapper::toCampaignDto)
                .collect(Collectors.toList());
    }

    public CampaignDto getCampaignById(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign with ID " + id + " not found"));
        return campaignMapper.toCampaignDto(campaign);
    }

    @Transactional
    public CampaignDto createCampaign(CampaignDto campaignDto) {
        Product product = productRepository.findById(campaignDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (campaignDto.getBidAmount().compareTo(MIN_BID_AMOUNT) < 0) {
            throw new RuntimeException("Bid amount must be at least " + MIN_BID_AMOUNT);
        }

        accountService.deductFunds(campaignDto.getCampaignFund());

        Campaign campaign = campaignMapper.toCampaign(campaignDto);
        campaign.setProduct(product);
        Campaign savedCampaign = campaignRepository.save(campaign);

        return campaignMapper.toCampaignDto(savedCampaign);
    }


    @Transactional
    public CampaignDto updateCampaign(Long id, CampaignDto campaignDto) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign with ID " + id + " not found"));

        Product product = productRepository.findById(campaignDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product with ID " + campaignDto.getProductId() + " not found"));

        campaign.setName(campaignDto.getName());
        campaign.setKeywords(campaignDto.getKeywords());
        campaign.setBidAmount(campaignDto.getBidAmount());
        campaign.setCampaignFund(campaignDto.getCampaignFund());
        campaign.setStatus(campaignDto.getStatus());
        campaign.setTown(campaignDto.getTown());
        campaign.setRadius(campaignDto.getRadius());
        campaign.setProduct(product);

        Campaign updatedCampaign = campaignRepository.save(campaign);
        return campaignMapper.toCampaignDto(updatedCampaign);
    }

    @Transactional
    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }

    public List<CampaignDto> getCampaignsByProduct(Long productId) {
        List<Campaign> campaigns = campaignRepository.findByProductId(productId);
        return campaigns.stream()
                .map(campaignMapper::toCampaignDto)
                .collect(Collectors.toList());
    }
}
