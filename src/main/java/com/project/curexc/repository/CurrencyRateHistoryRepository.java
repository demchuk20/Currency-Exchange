package com.project.curexc.repository;

import com.project.curexc.model.CurrencyRateHistory;
import com.project.curexc.repository.custom.CurrencyRateHistoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateHistoryRepository extends JpaRepository<CurrencyRateHistory, Integer>, CurrencyRateHistoryCustom {
}
