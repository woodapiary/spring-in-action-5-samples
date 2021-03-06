// tag::baseClass[]
package tacos.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
//end::baseClass[]
import org.springframework.web.bind.annotation.PostMapping;
//tag::baseClass[]
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.Order;

@Controller
@RequestMapping("/orders")
public class OrderController {

    static Logger log = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/current")
    public String orderForm(final Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }
    //end::orderForm[]

    /*
     * //tag::handlePost[]
     *
     * @PostMapping public String processOrder(Order order) { log.info("Order submitted: " + order); return "redirect:/"; }
     * //end::handlePost[]
     */

    //tag::handlePostWithValidation[]
    @PostMapping
    public String processOrder(@Valid final Order order, final Errors errors) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Order submitted: " + order);
        return "redirect:/";
    }
    //end::handlePostWithValidation[]

    //tag::baseClass[]

}
//end::baseClass[]
