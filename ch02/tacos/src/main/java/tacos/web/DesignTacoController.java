// tag::head[]
package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;

@Controller
@RequestMapping("/design")
public class DesignTacoController {

    //end::head[]
    static Logger log = LoggerFactory.getLogger(DesignTacoController.class);

    @ModelAttribute
    public void addIngredientsToModel(final Model model) {
        final List<Ingredient> ingredients = Arrays.asList(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP), new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES), new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE), new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE), new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

        final Type[] types = Ingredient.Type.values();
        for (final Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    //tag::showDesignForm[]
    @GetMapping
    public String showDesignForm(final Model model) {
        model.addAttribute("design", new Taco());
        return "design";
    }

    //end::showDesignForm[]

    /*
     * //tag::processDesign[]
     *
     * @PostMapping public String processDesign(Design design) { // Save the taco design... // We'll do this in chapter 3
     * log.info("Processing design: " + design);
     *
     * return "redirect:/orders/current"; }
     *
     * //end::processDesign[]
     */

    //tag::processDesignValidated[]
    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") final Taco design, final Errors errors, final Model model) {
        if (errors.hasErrors()) {
            return "design";
        }

        // Save the taco design...
        // We'll do this in chapter 3
        log.info("Processing design: " + design);

        return "redirect:/orders/current";
    }

    //end::processDesignValidated[]

    //tag::filterByType[]
    private List<Ingredient> filterByType(final List<Ingredient> ingredients, final Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    //end::filterByType[]
    // tag::foot[]
}
// end::foot[]
