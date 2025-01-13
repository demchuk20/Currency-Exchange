package com.project.curexc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "currency_rate_history")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyRateHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_rate_history_seq")
    @SequenceGenerator(name = "currency_rate_history_seq", sequenceName = "currency_rate_history_id_seq", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private Timestamp time;

    @Column(nullable = false)
    private BigDecimal rate;

    @ManyToOne
    @JoinColumn(nullable = false, name = "currency_id")
    private Currency currency;
}
