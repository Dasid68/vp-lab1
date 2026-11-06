package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Instructor;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Workshop> workshops = null;
    public static List<Registration> registrations = null;
    public static List<Instructor> instructors = null;

    @PostConstruct
    public void init(){
        workshops = new ArrayList<Workshop>();
        registrations = new ArrayList<Registration>();
        instructors = new ArrayList<Instructor>();

        instructors.add(new Instructor("Petar", "Trajkov", "ptrajkovs@mail.com ", "Programiranje"));
        instructors.add(new Instructor("Stefan", "Stefanovski", "sstefanovski@mail.com ", "Stolarstvo"));
        instructors.add(new Instructor("Ana", "Stojanovska", "astojanovska@mail.com ", "Gotvenje"));

        workshops.add(new Workshop("Rab1", "Drvodelstvo", 200, 0, instructors.get(1)));
        workshops.add(new Workshop("Rab2", "Keramika", 350, 12, instructors.get(1)));
        workshops.add(new Workshop("Rab3", "Fotografija", 500, 8, instructors.get(0)));
        workshops.add(new Workshop("Rab4", "Programiranje", 800, 20, instructors.get(0)));
        workshops.add(new Workshop("Rab5", "Crtanje", 250, 15, instructors.get(2)));
        workshops.add(new Workshop("Rab6", "Muzika", 600, 10, instructors.get(2)));
        workshops.add(new Workshop("Rab7", "Gotvenje", 450, 6, instructors.get(2)));
        workshops.add(new Workshop("Rab8", "Fotografija", 700, 4, instructors.get(0)));
        workshops.add(new Workshop("Rab9", "Drvodelstvo", 300, 9, instructors.get(1)));
        workshops.add(new Workshop("Rab10", "Programiranje", 900, 25, instructors.get(0)));



    }

}
