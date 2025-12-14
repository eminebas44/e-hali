package org.example.ehali.service.strategy;

import java.math.BigDecimal;

public interface IndirimStratejisi {
    BigDecimal indirimUygula(BigDecimal orijinalFiyat, BigDecimal indirimDegeri);
}
