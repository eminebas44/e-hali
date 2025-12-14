package org.example.ehali.entity.state;

import org.example.ehali.entity.Siparis;

public class KargodaDurumu implements SiparisDurumu {
    @Override
    public void sonrakiDurum(Siparis siparis) {
        siparis.setDurum(new TeslimEdildiDurumu());
        siparis.setDurumAdi("Teslim Edildi");
    }

    @Override
    public void iptalEt(Siparis siparis) {
        // Kargodaki bir sipariş genellikle iptal edilemez veya farklı bir süreç gerektirir.
        // Şimdilik bir RuntimeException fırlatabiliriz veya farklı bir durum belirleyebiliriz.
        throw new RuntimeException("Kargodaki sipariş iptal edilemez.");
    }

    @Override
    public String getDurumAdi() {
        return "Kargoda";
    }
}
