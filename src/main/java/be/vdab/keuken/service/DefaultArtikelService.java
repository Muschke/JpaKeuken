package be.vdab.keuken.service;

import be.vdab.keuken.exceptions.ArtikelNietGevondenException;
import be.vdab.keuken.repositories.ArtikelRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
class DefaultArtikelService implements ArtikelService{
    private final ArtikelRepository artikelRepository;

    public DefaultArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    @Override
    public void verhoogVerkoopPrijs(long id, BigDecimal waarde) {
        artikelRepository.findById(id)
                .orElseThrow(ArtikelNietGevondenException::new)
                .verhoogVerkoopPrijs(waarde);
    }
}
