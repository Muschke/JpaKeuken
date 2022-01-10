package be.vdab.keuken.service;

import java.math.BigDecimal;

public interface ArtikelService {
    void verhoogVerkoopPrijs(long id, BigDecimal waarde);
}
