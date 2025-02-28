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
public class Account {
    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private BigDecimal balance;
}
