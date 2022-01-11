package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;
import be.vdab.keuken.domain.FoodArtikel;
import be.vdab.keuken.domain.Korting;
import be.vdab.keuken.domain.NonFoodArtikel;
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
    private FoodArtikel foodArtikel;
    private NonFoodArtikel nonFoodArtikel;

    @BeforeEach
    void beforeEach() {
        foodArtikel = new FoodArtikel("testFoodArtikel", BigDecimal.ONE, BigDecimal.TEN, 10);
        nonFoodArtikel = new NonFoodArtikel("testNonFoodArtikel", BigDecimal.ONE, BigDecimal.TEN, 10);
    }

    @Test
    void findFoodArtikelById() {
        assertThat(repository.findById(idVanTestFoodArtikel()))
                .containsInstanceOf(FoodArtikel.class)
                .hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testFoodArtikel"));
    }
    @Test
    void findNonFoodArtikelById() {
        assertThat(repository.findById(idVanTestNonFoodArtikel()))
                .containsInstanceOf(NonFoodArtikel.class)
                .hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testNonFoodArtikel"));
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void createFoodArtikel() {
        repository.create(foodArtikel);
        assertThat(foodArtikel.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + foodArtikel.getId())).isOne();
    }
    @Test
    void createNonFoodArtikel() {
        repository.create(nonFoodArtikel);
        assertThat(nonFoodArtikel.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + nonFoodArtikel.getId())).isOne();
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
        assertThat(countRowsInTableWhere(ARTIKELS, "verkoopprijs = 3.3 and id = " + idVanTestFoodArtikel())).isOne();
    }

    @Test
    void kortingenLezen() {
        assertThat(repository.findById(idVanTestFoodArtikel()))
                .hasValueSatisfying(artikel -> assertThat(artikel.getKortingSet())
                        .containsOnly(new Korting(1, BigDecimal.TEN)));
    }

    private long idVanTestFoodArtikel() {
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testFoodArtikel'", Long.class);
    }

    private long idVanTestNonFoodArtikel() {
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testNonFoodArtikel'", Long.class);
    }


}