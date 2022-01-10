package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private static final String ARTIKELS = "artikels";

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
        this.repository = repository;
    }
    private Artikel artikel;

    @BeforeEach
    void beforeEach() {
        artikel = new Artikel("test", BigDecimal.valueOf(2.72), BigDecimal.valueOf(3.85));
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestArtikel()))
                .hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testArtikel"));
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void create() {
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }

    @Test
    void findArtikelByString() {
        var woord = "pp";
        assertThat(repository.findArtikelByString(woord))
                .hasSize(countRowsInTableWhere(ARTIKELS, "naam like '%pp%'"))
                .allSatisfy(naam -> assertThat(naam).containsIgnoringCase(woord))
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void verhoogAllePrijzen() {
        /* eerste test, we krijgen het aantal gewijzigde records terug, dus we kijken of het aantal gewijzigde records
        * gelijk is aan het totaal aantal*/
        assertThat(repository.verhoogAllePrijzen(BigDecimal.TEN))
                .isEqualTo(countRowsInTable(ARTIKELS));
        /*Nu gaan we checken dat ons testartikel met 10% gestegen is in verkoopprijs*/
        assertThat(countRowsInTableWhere(ARTIKELS, "verkoopprijs = 3.3 and id = " + idVanTestArtikel())).isOne();
    }


    private long idVanTestArtikel() {
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testArtikel'", Long.class);
    }
}