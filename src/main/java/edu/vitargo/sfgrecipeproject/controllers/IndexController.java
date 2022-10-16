package edu.vitargo.sfgrecipeproject.controllers;

import edu.vitargo.sfgrecipeproject.domain.Category;
import edu.vitargo.sfgrecipeproject.domain.UnitOfMeasure;
import edu.vitargo.sfgrecipeproject.repositories.CategoryRepository;
import edu.vitargo.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import edu.vitargo.sfgrecipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeService recipeService;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    String getIndex(Model model) {
        log.trace("Getting index page...");
        model.addAttribute("recipes", recipeService.getAllRecipe());
        log.trace("IndexController >>> Start getIndex");
        Optional<Category> categoryOptional = categoryRepository.getCategoryByDescription("Ukrainian");
        Optional<UnitOfMeasure> unitOfMeasureOptioanl = unitOfMeasureRepository.getUnitOfMeasureByDescription("Cup");
        Optional<Category> categoryOptionalEmpty = categoryRepository.getCategoryByDescription("");
        categoryOptional.ifPresentOrElse(e -> log.trace(e.getDescription() + " category id is " + e.getId()), () -> log.trace("Null"));
        unitOfMeasureOptioanl.ifPresentOrElse(e -> log.trace(e.getDescription() + " uom id is " + e.getId()), () -> log.trace("Null"));
        categoryOptionalEmpty.ifPresentOrElse(e -> log.trace(e.getDescription() + " category id is  " + e.getId()), () -> log.trace("Null"));
        log.trace("IndexController >>> Finish getIndex");

        return "index";
    }
}
