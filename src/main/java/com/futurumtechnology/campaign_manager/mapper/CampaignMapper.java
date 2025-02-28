package com.futurumtechnology.campaign_manager.mapper;

import com.futurumtechnology.campaign_manager.dto.CampaignDto;
import com.futurumtechnology.campaign_manager.entities.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CampaignMapper {

    @Mapping(source = "product.id", target = "productId")
    CampaignDto toCampaignDto(Campaign campaign);

    @Mapping(source = "productId", target = "product.id")
    Campaign toCampaign(CampaignDto campaignDto);

    List<CampaignDto> toCampaignDtoList(List<Campaign> campaigns);

    List<Campaign> toCampaignList(List<CampaignDto> campaignDtos);
}
