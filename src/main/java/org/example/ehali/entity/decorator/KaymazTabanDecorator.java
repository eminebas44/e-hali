package org.example.ehali.entity.decorator;

import org.example.ehali.entity.HaliArayuz;

import java.math.BigDecimal;

public class KaymazTabanDecorator extends HaliDecorator {

    public KaymazTabanDecorator(HaliArayuz sarmalananHali) {
        super(sarmalananHali);
    }

    @Override
    public BigDecimal getFiyat() {
        return super.getFiyat().add(new BigDecimal("150.00"));
    }

    @Override
    public String getAciklama() {
        return super.getAciklama() + ", Kaymaz Tabanlı";
    }
}
