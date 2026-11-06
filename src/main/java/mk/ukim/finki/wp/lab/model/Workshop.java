package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Workshop {
    private Long id;
    private String title;
    private String Category;
    private double price;
    private int capacity;
    private Instructor instructor;

    public Workshop(String title, String category, double price, int capacity, Instructor instructor) {
        this.title = title;
        Category = category;
        this.price = price;
        this.capacity = capacity;
        this.instructor = instructor;
        this.id = (long) (Math.random() * 1000);
    }


}
