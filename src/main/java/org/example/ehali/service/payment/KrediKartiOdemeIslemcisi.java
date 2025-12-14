package org.example.ehali.service.payment;

import java.math.BigDecimal;

public class KrediKartiOdemeIslemcisi implements OdemeIslemcisi {
    @Override
    public String odemeYap(BigDecimal tutar) {
        System.out.println(tutar + " TL, Kredi Kartı ile başarıyla ödendi.");
        return "Kredi Kartı ile Ödendi";
    }
}
