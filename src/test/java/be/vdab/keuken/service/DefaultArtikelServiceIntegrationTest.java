package be.vdab.keuken.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(DefaultArtikelService.class)
@ComponentScan(value = "be.vdab.keuken.repositories", resourcePattern = "JpaArtikelRepository.class")
@Sql("/insertArtikel.sql")
class DefaultArtikelServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String ARTIKELS = "artikels";
    private final DefaultArtikelService artikelService;
    private final EntityManager manager;

    public DefaultArtikelServiceIntegrationTest(DefaultArtikelService artikelService, EntityManager manager) {
        this.artikelService = artikelService;
        this.manager = manager;
    }

    private long idVanTestArtikel() {
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testArtikel'", long.class);
    }

    @Test
    void verhoogVerkoopPrijs() {
        var id = idVanTestArtikel();
        artikelService.verhoogVerkoopPrijs(id, BigDecimal.ONE);
        manager.flush();
        assertThat(countRowsInTableWhere(ARTIKELS, "verkoopprijs = 2.5 and id = " + id));
    }
}
