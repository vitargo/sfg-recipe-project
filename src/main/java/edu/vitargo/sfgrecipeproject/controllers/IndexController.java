package edu.vitargo.sfgrecipeproject.controllers;

import edu.vitargo.sfgrecipeproject.domain.Category;
import edu.vitargo.sfgrecipeproject.domain.UnitOfMeasure;
import edu.vitargo.sfgrecipeproject.repositories.CategoryRepository;
import edu.vitargo.sfgrecipeproject.repositories.RecipeRepository;
import edu.vitargo.sfgrecipeproject.repositories.UnitOfMeasureRepository;
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
    private final RecipeRepository recipeRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/", "/index"})
    String getIndex(Model model) {
        log.info("Getting index page...");
        model.addAttribute("recipes", recipeRepository.findAll());
        log.error("IndexController >>> Start getIndex");
        Optional<Category> categoryOptional = categoryRepository.getCategoryByDescription("Ukrainian");
        Optional<UnitOfMeasure> unitOfMeasureOptioanl = unitOfMeasureRepository.getUnitOfMeasureByDescription("Cup");
        Optional<Category> categoryOptionalEmpty = categoryRepository.getCategoryByDescription("");
        categoryOptional.ifPresentOrElse(e -> log.error(e.getDescription() + " category id is " + e.getId()), () -> log.error("Null"));
        unitOfMeasureOptioanl.ifPresentOrElse(e -> log.error(e.getDescription() + " uom id is " + e.getId()), () -> log.error("Null"));
        categoryOptionalEmpty.ifPresentOrElse(e -> log.error(e.getDescription() + " category id is  " + e.getId()), () -> log.error("Null"));
        log.error("IndexController >>> Finish getIndex");

        return "index";
    }

}
