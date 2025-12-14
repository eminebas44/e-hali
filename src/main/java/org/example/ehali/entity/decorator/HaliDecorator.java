package org.example.ehali.entity.decorator;

import org.example.ehali.entity.HaliArayuz;

import java.math.BigDecimal;

public abstract class HaliDecorator implements HaliArayuz {
    protected HaliArayuz sarmalananHali;

    public HaliDecorator(HaliArayuz sarmalananHali) {
        this.sarmalananHali = sarmalananHali;
    }

    @Override
    public BigDecimal getFiyat() {
        return sarmalananHali.getFiyat();
    }

    @Override
    public String getAciklama() {
        return sarmalananHali.getAciklama();
    }
}
