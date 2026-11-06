package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.repository.WorkshopRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryWorkshopRepository implements WorkshopRepository {

    private final DataHolder dataHolder;

    public InMemoryWorkshopRepository(DataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    @Override
    public List<Workshop> findAll() {
       return DataHolder.workshops;
    }

    @Override
    public Optional<Workshop> findByTitle(String title) {
        Optional<Workshop> ws =  DataHolder.workshops.stream().filter(w -> w.getTitle().equals(title)).findFirst();
        if(ws.isEmpty())
            return Optional.empty();
        return ws;
    }

    @Override
    public List<Workshop> search(String text, Double maxPrice) {

        boolean nemaText = text == null || text.isEmpty();

        if(nemaText && maxPrice == null){
            return DataHolder.workshops.stream().toList();
        }

        if(nemaText){
            return DataHolder.workshops.stream().filter(w -> w.getPrice() <= maxPrice).toList();
        }

        if(maxPrice == null){
            return DataHolder.workshops.stream().filter(w -> w.getTitle().toLowerCase().contains(text.toLowerCase())
            || w.getCategory().toLowerCase().contains(text.toLowerCase())
            ).toList();
        }

        return DataHolder.workshops.stream().filter(w -> (w.getTitle().toLowerCase().contains(text.toLowerCase())
                || w.getCategory().toLowerCase().contains(text.toLowerCase())) && w.getPrice() <= maxPrice
        ).toList();

    }

    @Override
    public Optional<Workshop> findById(Long id) {
        return DataHolder.workshops.stream().filter(w -> w.getId().equals(id)).findFirst();
    }

    @Override
    public Workshop save(Workshop workshop) {
        Workshop existingWorkshop = findById(workshop.getId()).orElse(null);

        if(existingWorkshop != null){
            existingWorkshop.setInstructor(workshop.getInstructor());
            existingWorkshop.setCapacity(workshop.getCapacity());
            existingWorkshop.setCategory(workshop.getCategory());
            existingWorkshop.setPrice(workshop.getPrice());
            existingWorkshop.setTitle(workshop.getTitle());

            DataHolder.workshops.remove(existingWorkshop);
            DataHolder.workshops.add(existingWorkshop);

            return existingWorkshop;

        }

        DataHolder.workshops.add(workshop);

        return workshop;
    }

    @Override
    public void deleteById(Long id) {
        Workshop ws = DataHolder.workshops.stream().filter(w -> w.getId().equals(id)).findFirst().orElse(null);
        DataHolder.workshops.remove(ws);
    }
}
