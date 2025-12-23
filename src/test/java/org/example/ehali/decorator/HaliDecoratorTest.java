package org.example.ehali.decorator;

import org.example.ehali.entity.HaliArayuz;
import org.example.ehali.entity.decorator.HaliDecorator;
import org.example.ehali.entity.decorator.KaymazTabanDecorator;
import org.example.ehali.entity.decorator.LekeTutmazKaplamaDecorator;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class HaliDecoratorTest {

    private final HaliArayuz testHalisi = new HaliArayuz() {
        @Override
        public BigDecimal getFiyat() {
            return new BigDecimal("1000.00");
        }

        @Override
        public String getAciklama() {
            return "Standart Halı";
        }
    };
    
@SpringBootTest
@ActiveProfiles("test")
    void kaymazTabanFiyatArtisiTesti() {
        HaliDecorator hali = new KaymazTabanDecorator(testHalisi);
        assertEquals(new BigDecimal("1150.00"), hali.getFiyat());
    }

  @SpringBootTest
@ActiveProfiles("test")
    void lekeTutmazAciklamaTesti() {
        HaliDecorator hali = new LekeTutmazKaplamaDecorator(testHalisi);
        assertTrue(hali.getAciklama().contains("Leke Tutmaz"));
    }
}
