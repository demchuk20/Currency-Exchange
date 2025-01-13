package com.project.curexc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "currency")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq")
    @SequenceGenerator(name = "currency_seq", sequenceName = "currency_id_seq", allocationSize = 1)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    private String name;
}
