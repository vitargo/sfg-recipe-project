package edu.vitargo.sfgrecipeproject.controllers;

import edu.vitargo.sfgrecipeproject.commands.IngredientCommand;
import edu.vitargo.sfgrecipeproject.commands.RecipeCommand;
import edu.vitargo.sfgrecipeproject.commands.UnitOfMeasureCommand;
import edu.vitargo.sfgrecipeproject.services.IngredientService;
import edu.vitargo.sfgrecipeproject.services.RecipeService;
import edu.vitargo.sfgrecipeproject.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static edu.vitargo.sfgrecipeproject.utils.Constants.INGREDIENT_ATTR;

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
    @RequestMapping("recipe/{recipeId}/ingredients")
    public String getAllIngredients(@PathVariable String recipeId, Model model){
        log.debug("Get list of ingredients for recipe id = {}", recipeId);
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.parseLong(recipeId));
        if (recipeCommand == null){
            return "error/errorpage";
        }
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(recipeId)));
        return "recipe/ingredients/list";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String getIngredient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId,
                                 Model model){
        log.debug("Get ingredient for recipe id = {}, ingredient id = {}", recipeId, ingredientId);
        model.addAttribute(INGREDIENT_ATTR, ingredientService.getIngredientByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(ingredientId)));
        return "recipe/ingredients/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId,
                                @PathVariable String ingredientId,
                                Model model) {
        log.debug("Update ingredient for recipe id = {}, ingredient id = {}", recipeId, ingredientId);
        model.addAttribute(INGREDIENT_ATTR, ingredientService.getIngredientByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.getAllUom());
        return "recipe/ingredients/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("Save recipe id = {}, ingredient id = {}", savedCommand.getRecipeId(), savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){
        log.debug("Get ingredient for recipe id = {}", recipeId);
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.parseLong(recipeId));
        if (recipeCommand == null){
            return "error/errorpage";
        }
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        ingredientCommand.setUnitOfMeasureCommand(UnitOfMeasureCommand.builder().id(1L).build());
        model.addAttribute(INGREDIENT_ATTR, ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.getAllUom());
        return "recipe/ingredients/ingredientform";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId){
        log.debug("Delete ingredient for recipe id = {}, ingredient id = {}", recipeId, ingredientId);
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.parseLong(recipeId));
        if(recipeCommand == null){
            return "recipe/errorpage";
        }
        ingredientService.deleteIngredientByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(ingredientId));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
