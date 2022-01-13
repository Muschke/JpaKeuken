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
    private ArtikelGroep artikelGroep1, artikelGroep2;

    @BeforeEach
    void beforeEach() {
        artikelGroep1 = new ArtikelGroep("test");
        artikelGroep2 = new ArtikelGroep("test2");
        foodArtikel = new FoodArtikel("testFoodArtikel", BigDecimal.ONE, BigDecimal.TEN, 10, artikelGroep1);
        nonFoodArtikel = new NonFoodArtikel("testNonFoodArtikel", BigDecimal.ONE, BigDecimal.TEN, 10, artikelGroep2);
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

    //extra methodes voor bidirectionele manyToOne, artikel is de many kant
    @Test
    void groep1EnFoodArtikelZijnVerbonden() {
        assertThat(artikelGroep1.getArtikelSet()).containsOnly(foodArtikel);
        assertThat(foodArtikel.getArtikelGroep()).isEqualTo(artikelGroep1);
    }
    @Test
    void groep2EnNonFoodArtikelZijnVerbonden() {
        assertThat(artikelGroep2.getArtikelSet()).containsOnly(nonFoodArtikel);
        assertThat(nonFoodArtikel.getArtikelGroep()).isEqualTo(artikelGroep2);
    }

    @Test
    void artikelVerhuistNaarGroep2() {
        foodArtikel.setArtikelGroep(artikelGroep2);
        assertThat(artikelGroep1.getArtikelSet()).doesNotContain(foodArtikel);
        assertThat(artikelGroep2.getArtikelSet()).containsOnly(foodArtikel, nonFoodArtikel);
    }

    @Test
    void nullAlsArtikelGroepInDeConstructorMislukt() {
        assertThatNullPointerException().isThrownBy(
                () -> new FoodArtikel("test", BigDecimal.ONE, BigDecimal.ONE, 1, null));
    }
    @Test
    void nullAlsArtikelGroepInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(
                () -> foodArtikel.setArtikelGroep(null));
    }
}