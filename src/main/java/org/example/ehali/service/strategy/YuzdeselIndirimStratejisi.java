package org.example.ehali.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class YuzdeselIndirimStratejisi implements IndirimStratejisi {
    @Override
    public BigDecimal indirimUygula(BigDecimal orijinalFiyat, BigDecimal indirimOrani) {
        BigDecimal indirimMiktari = orijinalFiyat.multiply(indirimOrani.divide(new BigDecimal("100")));
        return orijinalFiyat.subtract(indirimMiktari).setScale(2, RoundingMode.HALF_UP);
    }
}
