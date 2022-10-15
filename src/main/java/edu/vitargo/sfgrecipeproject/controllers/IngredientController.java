package edu.vitargo.sfgrecipeproject.controllers;

import edu.vitargo.sfgrecipeproject.services.IngredientService;
import edu.vitargo.sfgrecipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/ingredients")
    public String getIngredients(@PathVariable String id, Model model){
        log.debug("Get list of ingredients for recipe id = {}", id);
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
        return "recipe/ingredients/list";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String getIngredients(@PathVariable String recipeId,
                                 @PathVariable String ingredientId,
                                 Model model){
        log.debug("Get list of ingredients for recipe id = {}, ingredient id = {}", recipeId, ingredientId);
        model.addAttribute("ingredient", ingredientService.getIngredientByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(ingredientId)));
        return "recipe/ingredients/show";
    }
}
