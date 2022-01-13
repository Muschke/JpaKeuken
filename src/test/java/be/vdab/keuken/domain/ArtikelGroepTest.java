package be.vdab.keuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.*;

class ArtikelGroepTest {
    private ArtikelGroep artikelGroep1;
    private ArtikelGroep artikelGroep2;
    private Artikel artikel;

    @BeforeEach
    void beforeEach() {
        artikelGroep1 = new ArtikelGroep("test");
        artikelGroep2 = new ArtikelGroep("test2");
        artikel = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.ONE, 1, artikelGroep1);
    }

    @Test
    void groep1IsDeArtikelGroepVanArtikel1() {
        assertThat(artikel.getArtikelGroep()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep1.getArtikelSet()).containsOnly(artikel);
    }

    @Test
    void artikel1VerhuistNaarGroep2() {
        assertThat(artikelGroep2.add(artikel)).isTrue();
        assertThat(artikelGroep1.getArtikelSet()).doesNotContain(artikel);
        assertThat(artikelGroep2.getArtikelSet()).containsOnly(artikel);
    }

    @Test
    void nullAlsArtikelToevoegenMislukt() {
        assertThatNullPointerException().isThrownBy(
                ()-> artikelGroep1.add(null));
    }
}