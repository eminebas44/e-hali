package org.example.ehali.entity.state;

import org.example.ehali.entity.Siparis;

public class TeslimEdildiDurumu implements SiparisDurumu {
    @Override
    public void sonrakiDurum(Siparis siparis) {
        // Son durum, daha ileri gidemez.
        System.out.println("Sipariş zaten teslim edilmiş.");
    }

    @Override
    public void iptalEt(Siparis siparis) {
        // Teslim edilmiş sipariş iptal edilemez.
        throw new RuntimeException("Teslim edilmiş sipariş iptal edilemez.");
    }

    @Override
    public String getDurumAdi() {
        return "Teslim Edildi";
    }
}
