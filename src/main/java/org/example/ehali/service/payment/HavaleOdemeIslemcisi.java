package org.example.ehali.service.payment;

import java.math.BigDecimal;

public class HavaleOdemeIslemcisi implements OdemeIslemcisi {
    @Override
    public String odemeYap(BigDecimal tutar) {
        System.out.println(tutar + " TL, Havale/EFT için ödeme referansı oluşturuldu.");
        return "Havale/EFT Bekleniyor";
    }
}
