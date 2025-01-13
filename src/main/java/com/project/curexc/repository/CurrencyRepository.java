package com.project.curexc.repository;

import com.project.curexc.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findByCode(String code);
}
