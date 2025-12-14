package org.example.ehali.entity.state;

import org.example.ehali.entity.Siparis;

public class BeklemedeDurumu implements SiparisDurumu {
    @Override
    public void sonrakiDurum(Siparis siparis) {
        siparis.setDurum(new HazirlaniyorDurumu());
        siparis.setDurumAdi("Hazırlanıyor");
    }

    @Override
    public void iptalEt(Siparis siparis) {
        siparis.setDurum(new IptalEdildiDurumu());
        siparis.setDurumAdi("İptal Edildi");
    }

    @Override
    public String getDurumAdi() {
        return "Beklemede";
    }
}
