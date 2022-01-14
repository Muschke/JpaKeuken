package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaArtikelRepository implements ArtikelRepository{
    private final EntityManager manager;


    public JpaArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Artikel> findById(long id) {
        return Optional.ofNullable(manager.find(Artikel.class, id));
    }
    @Override
    public void create(Artikel artikel) {
        manager.persist(artikel);
    }

    @Override
    public List<Artikel> findArtikelByString(String string){
        return manager.createNamedQuery("Artikel.findArtikelByString", Artikel.class)
                .setParameter("string", '%' + string + '%')
                .setHint("javax.persistence.loadgraph", manager.createEntityGraph(Artikel.MET_ARTIKELGROEP))
                .getResultList();
    }

    @Override
    public int verhoogAllePrijzen(BigDecimal percentage) {
        return manager.createNamedQuery("Artikel.verhoogAllePrijzen")
                .setParameter("percentage", percentage)
                .executeUpdate();
    }


}
