package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Instructor;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.repository.InstructorRepository;
import mk.ukim.finki.wp.lab.repository.WorkshopRepository;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopServiceImpl implements WorkshopService {
    private final WorkshopRepository workshopRepository;
    private final InstructorRepository instructorRepository;

    public WorkshopServiceImpl(WorkshopRepository wr, InstructorRepository ir){
        workshopRepository = wr;
        instructorRepository = ir;
    }


    @Override
    public List<Workshop> listAll() {
        return workshopRepository.findAll();
    }

    @Override
    public List<Workshop> search(String text, Double maxPrice) {
        return workshopRepository.search(text, maxPrice);
    }

    @Override
    public Optional<Workshop> findById(Long id) {
        return workshopRepository.findById(id);
    }

    @Override
    public Workshop create(String title, String category, Double price, Integer capacity, Long instructorId) {
        if( (title == null && title.isEmpty()) || (category == null && category.isEmpty()) )
            throw new IllegalArgumentException();
        if( (price == null && price.doubleValue() < 0) || (capacity == null && capacity.intValue() < 0) )
            throw new IllegalArgumentException();

        if(instructorRepository.findById(instructorId) == null)
            throw new IllegalArgumentException();

        Workshop ws = new Workshop(title, category, price, capacity, instructorRepository.findById(instructorId).orElse(null));

        workshopRepository.save(ws);

        return ws;
    }

    @Override
    public Workshop update(Long id, String title, String category, Double price, Integer capacity, Long instructorId) {
        if( (title == null && title.isEmpty()) || (category == null && category.isEmpty()) )
            throw new IllegalArgumentException();
        if( (price == null && price.doubleValue() < 0) || (capacity == null && capacity.intValue() < 0) )
            throw new IllegalArgumentException();

        if(instructorRepository.findById(instructorId) == null)
            throw new IllegalArgumentException();

        Instructor ins = instructorRepository.findById(instructorId).orElse(null);

        Workshop ws = new Workshop(id,title, category, price, capacity, ins);

        return workshopRepository.save(ws);

    }

    @Override
    public void deleteById(Long id) {
        workshopRepository.deleteById(id);
    }
}
