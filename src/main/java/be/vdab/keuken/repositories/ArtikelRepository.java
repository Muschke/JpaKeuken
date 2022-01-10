package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
    void create(Artikel artikel);
    List<String> findArtikelByString(String string);
    int verhoogAllePrijzen(BigDecimal percentage);
}
