package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

// tag::classShell[]
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    //end::classShell[]

    //tag::bothRepoProperties[]
    //tag::ingredientRepoProperty[]
    private final IngredientRepository ingredientRepo;

    //end::ingredientRepoProperty[]
    private final TacoRepository designRepo;

    //end::bothRepoProperties[]

    /*
     * // tag::ingredientRepoOnlyCtor[]
     *
     * @Autowired public DesignTacoController(IngredientRepository ingredientRepo) { this.ingredientRepo = ingredientRepo; }
     * // end::ingredientRepoOnlyCtor[]
     */

    // tag::bothRepoCtor[]
    @Autowired
    public DesignTacoController(final IngredientRepository ingredientRepo, final TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    // end::bothRepoCtor[]

    // tag::modelAttributes[]
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    // end::modelAttributes[]
    // tag::showDesignForm[]

    @GetMapping
    public String showDesignForm(final Model model) {
        final List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        final Type[] types = Ingredient.Type.values();
        for (final Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        return "design";
    }
    //end::showDesignForm[]

    // tag::processDesign[]
    @PostMapping
    public String processDesign(@Valid final Taco design, final Errors errors, @ModelAttribute final Order order) {

        if (errors.hasErrors()) {
            return "design";
        }

        final Taco saved = designRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }
    // end::processDesign[]

    private List<Ingredient> filterByType(final List<Ingredient> ingredients, final Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    /*
     * //tag::classShell[]
     *
     * ...
     *
     * //end::classShell[]
     */
    //tag::classShell[]

}
//end::classShell[]