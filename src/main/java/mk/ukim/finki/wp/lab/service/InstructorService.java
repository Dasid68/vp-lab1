package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InstructorService {
    List<Instructor> findAll();
}
