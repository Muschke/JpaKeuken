package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public List<String> findArtikelByString(String string){
        return manager.createQuery("select d.naam from Artikel d where d.naam like :string", String.class)
                .setParameter("string", '%' + string + '%')
                .getResultList();
    }
}
