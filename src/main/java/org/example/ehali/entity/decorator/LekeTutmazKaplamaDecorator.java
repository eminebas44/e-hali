package org.example.ehali.entity.decorator;

import org.example.ehali.entity.HaliArayuz;

import java.math.BigDecimal;

public class LekeTutmazKaplamaDecorator extends HaliDecorator {

    public LekeTutmazKaplamaDecorator(HaliArayuz sarmalananHali) {
        super(sarmalananHali);
    }

    @Override
    public BigDecimal getFiyat() {
        return super.getFiyat().add(new BigDecimal("250.00"));
    }

    @Override
    public String getAciklama() {
        return super.getAciklama() + ", Leke Tutmaz Kaplamalı";
    }
}
