package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.service.InstructorService;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
@RequestMapping("")
public class WorkshopController {

    private final WorkshopService workshopService;
    private final InstructorService instructorService;

    public WorkshopController(WorkshopService workshopService,InstructorService instructorService){
        this.instructorService = instructorService;
        this.workshopService = workshopService;
    }

    @GetMapping("/workshops")
    public String getWorkshopsPage(@RequestParam(required = false) String text,
                                   @RequestParam(required = false) Double maxPrice,
                                   @RequestParam(required = false) String errorMessage,
                                   Model model)
    {

        if(text != null && !text.isEmpty() || maxPrice != null && !maxPrice.isNaN()){
            try {
                model.addAttribute("workShops", workshopService.search(text, maxPrice));
                return "listWorkshops.html";
            } catch (Exception e){
                model.addAttribute("errorMessage", e.getMessage());
                return "listWorkshops.html";
            }
        }

        model.addAttribute("workShops", workshopService.listAll());
        model.addAttribute("errorMessage", errorMessage);
        return "listWorkshops.html";


    }

    @GetMapping("/")
    public String getIndexPage(){
        return "redirect:/workshops";
    }

    @PostMapping("/workshops/add")
    public String saveWorkshop(@RequestParam String title,
                               @RequestParam String category,
                               @RequestParam Double price,
                               @RequestParam Integer capacity,
                               @RequestParam Long instructorId)
    {
        try {
            workshopService.create(title, category, price, capacity, instructorId);

        } catch (Exception e){
            var message = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "/workshops?errorMessage=" + message;
        }

        return "redirect:/workshops";
    }

    @PostMapping("/workshops/edit/{workshopId}")
    public String editWorkshop(@PathVariable Long workshopId,
                               @RequestParam String title,
                               @RequestParam String category,
                               @RequestParam Double price,
                               @RequestParam Integer capacity,
                               @RequestParam Long instructorId)
    {
        try {
            workshopService.update(workshopId, title, category, price, capacity, instructorId);

        } catch (Exception e){
            var message = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "/workshops?errorMessage=" + message;
        }

        return "redirect:/workshops";
    }

    @PostMapping("/workshops/delete/{id}")
    public String deleteWorkshop(@PathVariable Long id){
        workshopService.deleteById(id);

        return "redirect:/workshops";
    }

    @GetMapping("/workshops/workshop-form")
    public String getAddWorkshopPage(Model model){
        model.addAttribute("instructors", instructorService.findAll());
        model.addAttribute("workshop", null);
        return "workshop-form.html";
    }

    @GetMapping("/workshops/workshop-form/{id}")
    public String getEditWorkshopPage( @PathVariable Long id,Model model){

        Optional<Workshop> ws = workshopService.findById(id);

        if(ws.isEmpty()){
            var message = URLEncoder.encode("WorkshopNotFound", StandardCharsets.UTF_8);
            return "redirect:/workshops?errorMessage=" + message;
        }

        model.addAttribute("workshop", ws.get());
        model.addAttribute("instructors", instructorService.findAll());

        return "workshop-form.html";

    }


}
