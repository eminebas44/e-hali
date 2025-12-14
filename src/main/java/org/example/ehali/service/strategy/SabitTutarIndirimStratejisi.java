package org.example.ehali.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SabitTutarIndirimStratejisi implements IndirimStratejisi {
    @Override
    public BigDecimal indirimUygula(BigDecimal orijinalFiyat, BigDecimal indirimTutari) {
        BigDecimal yeniFiyat = orijinalFiyat.subtract(indirimTutari);
        return yeniFiyat.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : yeniFiyat.setScale(2, RoundingMode.HALF_UP);
    }
}
