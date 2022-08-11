package edu.vitargo.sfgrecipeproject.controllers;

import edu.vitargo.sfgrecipeproject.domain.Category;
import edu.vitargo.sfgrecipeproject.domain.UnitOfMeasure;
import edu.vitargo.sfgrecipeproject.repositories.CategoryRepository;
import edu.vitargo.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    String getIndex() {
        log.error("IndexController >>> getIndex");
        Optional<Category> categoryOptional = categoryRepository.getCategoryByDescription("Ukrainian");
        Optional<UnitOfMeasure> unitOfMeasureOptioanl = unitOfMeasureRepository.getUnitOfMeasureByDescription("Cup");
        Optional<Category> categoryOptionalEmpty = categoryRepository.getCategoryByDescription("");
        categoryOptional.ifPresentOrElse(e -> log.error(e.getDescription() + " category id's is " + e.getId()), () -> log.error("Null"));
        unitOfMeasureOptioanl.ifPresentOrElse(e -> log.error(e.getDescription() + " nom id's is " + e.getId()), () -> log.error("Null"));
        categoryOptionalEmpty.ifPresentOrElse(e -> log.error(e.getDescription() + " category id's is  " + e.getId()), () -> log.error("Null"));
        return "index";
    }
}
