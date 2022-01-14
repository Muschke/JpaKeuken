package be.vdab.keuken.domain;


import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table (name="artikelgroepen")
public class ArtikelGroep {
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private long id;
    private String naam;
    @OneToMany(mappedBy = "artikelGroep") @OrderBy("naam")
    private Set<Artikel> artikelSet;


    public ArtikelGroep(String naam) {
        this.naam = naam;
        this.artikelSet = new LinkedHashSet<>();
    }

    protected ArtikelGroep() {};

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Set<Artikel> getArtikelSet() {
        return Collections.unmodifiableSet(artikelSet);
    }
    public boolean add(Artikel artikel) {
        var toegevoegd = artikelSet.add(artikel);
        var oudeArtikelGroep = artikel.getArtikelGroep();
        if(oudeArtikelGroep != null && oudeArtikelGroep != this) {
            oudeArtikelGroep.artikelSet.remove(artikel);
        }
        if (this != oudeArtikelGroep) {
            artikel.setArtikelGroep(this);
        }
        return toegevoegd;
    }
}
