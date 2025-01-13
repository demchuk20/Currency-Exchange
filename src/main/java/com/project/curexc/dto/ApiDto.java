package com.project.curexc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public interface ApiDto extends Serializable {
    Timestamp getTimestamp();

    String getBase();

    String getCurrencyCode();

    BigDecimal getRate();
}
