package org.example.ehali.entity.state;

import org.example.ehali.entity.Siparis;

public class HazirlaniyorDurumu implements SiparisDurumu {
    @Override
    public void sonrakiDurum(Siparis siparis) {
        siparis.setDurum(new KargodaDurumu());
        siparis.setDurumAdi("Kargoda");
    }

    @Override
    public void iptalEt(Siparis siparis) {
        siparis.setDurum(new IptalEdildiDurumu());
        siparis.setDurumAdi("İptal Edildi");
    }

    @Override
    public String getDurumAdi() {
        return "Hazırlanıyor";
    }
}
