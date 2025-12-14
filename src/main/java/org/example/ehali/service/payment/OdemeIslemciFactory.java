package org.example.ehali.service.payment;

import org.springframework.stereotype.Component;

@Component
public class OdemeIslemciFactory {

    public OdemeIslemcisi getIslemci(String odemeTipi) {
        if (odemeTipi == null) {
            return null;
        }
        if (odemeTipi.equalsIgnoreCase("KREDI_KARTI")) {
            return new KrediKartiOdemeIslemcisi();
        } else if (odemeTipi.equalsIgnoreCase("HAVALE")) {
            return new HavaleOdemeIslemcisi();
        }
        
        return null;
    }
}
