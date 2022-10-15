package edu.vitargo.sfgrecipeproject.controllers;

import edu.vitargo.sfgrecipeproject.commands.IngredientCommand;
import edu.vitargo.sfgrecipeproject.services.IngredientService;
import edu.vitargo.sfgrecipeproject.services.RecipeService;
import edu.vitargo.sfgrecipeproject.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/ingredients")
    public String getAllIngredients(@PathVariable String id, Model model){
        log.debug("Get list of ingredients for recipe id = {}", id);
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
        return "recipe/ingredients/list";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String getIngredient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId,
                                 Model model){
        log.debug("Get ingredient for recipe id = {}, ingredient id = {}", recipeId, ingredientId);
        model.addAttribute("ingredient", ingredientService.getIngredientByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(ingredientId)));
        return "recipe/ingredients/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId,
                                @PathVariable String ingredientId,
                                Model model) {
        log.debug("Update ingredient for recipe id = {}, ingredient id = {}", recipeId, ingredientId);
        model.addAttribute("ingredient", ingredientService.getIngredientByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.getAllUom());
        return "recipe/ingredients/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

//        log.debug("Save recipe id = {}, ingredient id = {}", savedCommand.getRecipeId(), savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }
}
