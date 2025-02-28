package com.futurumtechnology.campaign_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class ProductDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String category;

}