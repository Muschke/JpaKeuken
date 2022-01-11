package be.vdab.keuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ArtikelTest {
    private final static BigDecimal VERKOOPPRIJS = BigDecimal.TEN;
    private FoodArtikel foodArtikel;
    private NonFoodArtikel nonFoodArtikel;

    @BeforeEach
    void beforeEach() {
        foodArtikel = new FoodArtikel("testFoodArtikel", BigDecimal.ONE, BigDecimal.TEN, 10);
        nonFoodArtikel = new NonFoodArtikel("testNonFoodArtikel", BigDecimal.ONE, BigDecimal.TEN, 10);
    }

    @Test
    void verhoogVerkoopPrijs() {
        foodArtikel.verhoogVerkoopPrijs(BigDecimal.ONE);
        assertThat(foodArtikel.getVerkoopprijs()).isEqualByComparingTo("11");
        nonFoodArtikel.verhoogVerkoopPrijs(BigDecimal.ONE);
        assertThat(nonFoodArtikel.getVerkoopprijs()).isEqualByComparingTo("11");
    }

    @Test
    void verhoogVerkoopPrijsMet0ofNegatiefGetalMislukt() {
        assertThatIllegalArgumentException().isThrownBy(
                ()->foodArtikel.verhoogVerkoopPrijs(BigDecimal.ZERO));
        assertThatIllegalArgumentException().isThrownBy(
                ()->foodArtikel.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
        assertThatIllegalArgumentException().isThrownBy(
                ()->nonFoodArtikel.verhoogVerkoopPrijs(BigDecimal.ZERO));
        assertThatIllegalArgumentException().isThrownBy(
                ()->nonFoodArtikel.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
    }
    @Test
    void verhoogVerkoopPrijsMetNullMislukt() {
        assertThatNullPointerException().isThrownBy(
                ()-> foodArtikel.verhoogVerkoopPrijs(null));
        assertThatNullPointerException().isThrownBy(
                ()-> nonFoodArtikel.verhoogVerkoopPrijs(null));
    }
}