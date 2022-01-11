package be.vdab.keuken.service;

import be.vdab.keuken.domain.Artikel;
import be.vdab.keuken.domain.FoodArtikel;
import be.vdab.keuken.domain.NonFoodArtikel;
import be.vdab.keuken.exceptions.ArtikelNietGevondenException;
import be.vdab.keuken.repositories.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultArtikelServiceTest {
    private DefaultArtikelService artikelService;
    @Mock
    private ArtikelRepository artikelRepository;
    private Artikel artikel;

    @BeforeEach
    void beforeEach() {
        artikelService = new DefaultArtikelService(artikelRepository);
    }


    @Test
    void verhoogVerkoopPrijs() {
        when(artikelRepository.findById(1)).thenReturn(Optional.of(artikel));
        artikelService.verhoogVerkoopPrijs(1, BigDecimal.ONE);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("11");
    }

    @Test
    void verhogenVanOnbestaandArtikelGaatNiet() {
        assertThatExceptionOfType(ArtikelNietGevondenException.class).isThrownBy(
                ()-> artikelService.verhoogVerkoopPrijs(-1, BigDecimal.ONE));
        verify(artikelRepository).findById(-1);
    }
}