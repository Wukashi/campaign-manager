package com.futurumtechnology.campaign_manager.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String keywords;

    @Column(nullable = false)
    private BigDecimal bidAmount;

    @Column(nullable = false)
    private BigDecimal campaignFund;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private int radius;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
