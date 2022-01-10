package be.vdab.keuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ArtikelTest {
    private final static BigDecimal VERKOOPPRIJS = BigDecimal.TEN;
    private Artikel artikelEen;

    @BeforeEach
    void beforeEach() {
        artikelEen = new Artikel("test", BigDecimal.ONE, VERKOOPPRIJS);
    }

    @Test
    void verhoogVerkoopPrijs() {
        artikelEen.verhoogVerkoopPrijs(BigDecimal.ONE);
        assertThat(artikelEen.getVerkoopprijs()).isEqualByComparingTo("11");
    }

    @Test
    void verhoogVerkoopPrijsMet0ofNegatiefGetalMislukt() {
        assertThatIllegalArgumentException().isThrownBy(
                ()->artikelEen.verhoogVerkoopPrijs(BigDecimal.ZERO));
        assertThatIllegalArgumentException().isThrownBy(
                ()->artikelEen.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
    }
    @Test
    void verhoogVerkoopPrijsMetNullMislukt() {
        assertThatNullPointerException().isThrownBy(
                ()-> artikelEen.verhoogVerkoopPrijs(null));
    }
}