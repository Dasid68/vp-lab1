package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(urlPatterns = "")
public class WorkshopListServlet extends HttpServlet {
    private final WorkshopService workShopService;
    private final SpringTemplateEngine springTemplateEngine;

    public WorkshopListServlet(WorkshopService ws, SpringTemplateEngine ste){
        workShopService = ws;
        springTemplateEngine = ste;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String text = req.getParameter("text");
        String price = req.getParameter("maxPrice");

        Iterable<Workshop> workShops = null;

        if(text != null && !text.isEmpty() || price != null && !price.isEmpty()){
            Double parsedPrice = null;
            if( price != null && !price.isEmpty()){
                parsedPrice = Double.parseDouble(price);
            }

            workShops = workShopService.search(text, parsedPrice);

        } else {
            workShops = workShopService.listAll();
        }

        context.setVariable("workShops", workShops);
        context.setVariable("errorMessage", req.getParameter("errorMessage"));

        springTemplateEngine.process("listWorkshops.html", context, resp.getWriter());
    }
}
