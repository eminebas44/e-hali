package org.example.ehali.entity.state;

import org.example.ehali.entity.Siparis;

public class IptalEdildiDurumu implements SiparisDurumu {
    @Override
    public void sonrakiDurum(Siparis siparis) {
        System.out.println("İptal edilmiş siparişin durumu değiştirilemez.");
    }

    @Override
    public void iptalEt(Siparis siparis) {
        System.out.println("Sipariş zaten iptal edilmiş.");
    }

    @Override
    public String getDurumAdi() {
        return "İptal Edildi";
    }
}
