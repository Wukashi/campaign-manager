package com.futurumtechnology.campaign_manager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class CampaignDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String keywords;

    @NotNull
    @Min(1)
    private BigDecimal bidAmount;

    @NotNull
    @Min(1)
    private BigDecimal campaignFund;

    @NotNull
    private Boolean status;

    @NotNull
    private String town;

    @NotNull
    @Min(1)
    private int radius;

    @NotNull
    private Long productId;
}
