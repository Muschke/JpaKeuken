package be.vdab.keuken.domain;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="artikels")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "soort")
public abstract class Artikel {
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id private long id;
    private String naam;
    private BigDecimal aankoopprijs;
    private BigDecimal verkoopprijs;
    @ElementCollection @CollectionTable(name = "kortingen", joinColumns = @JoinColumn(name = "artikelId"))
    @OrderBy("vanafAantal")
    private Set<Korting> kortingSet;

    public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs) {
        this.naam = naam;
        this.aankoopprijs = aankoopprijs;
        this.verkoopprijs = verkoopprijs;
        this.kortingSet = new LinkedHashSet<>();
    }

    protected Artikel() {};

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public BigDecimal getAankoopprijs() {
        return aankoopprijs;
    }

    public BigDecimal getVerkoopprijs() {
        return verkoopprijs;
    }

    public void verhoogVerkoopPrijs(BigDecimal bedrag) {
        if(bedrag.compareTo(BigDecimal.ZERO)<=0) {
            throw new IllegalArgumentException();
        }
        verkoopprijs = verkoopprijs.add(bedrag);
    }

    public Set<Korting> getKortingSet() {
        return Collections.unmodifiableSet(kortingSet);
    }
}
