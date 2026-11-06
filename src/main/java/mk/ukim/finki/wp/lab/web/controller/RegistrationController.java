package mk.ukim.finki.wp.lab.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping("")
    public String register(
            @RequestParam String participantName,
            @RequestParam String participantEmail,
            @RequestParam Integer numSeats,
            @RequestParam String workshopTitle,
            HttpServletRequest request,
            @RequestHeader(name = "User-Agent", required = false) String userAgent,
            Model model
    ){
        var ipAddress = request.getRemoteAddr();
        try {
            registrationService.register(workshopTitle, participantName, participantEmail, numSeats);
            model.addAttribute("participantName", participantName);
            model.addAttribute("participantEmail", participantEmail);
            model.addAttribute("numberOfSeats", numSeats);
            model.addAttribute("workshopTitle", workshopTitle);
            model.addAttribute("ipAddress", ipAddress);
            model.addAttribute("userAgent", userAgent);
            return "registrationConfirmation.html";
        } catch (Exception e){
            return "redirect:/workshops?errorMessage=" + e.getMessage();
        }
    }


}
