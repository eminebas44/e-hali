package org.example.ehali.entity.state;

import org.example.ehali.entity.Siparis;

public interface SiparisDurumu {
    void sonrakiDurum(Siparis siparis);
    void iptalEt(Siparis siparis);
    String getDurumAdi();
}