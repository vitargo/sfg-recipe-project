package edu.vitargo.sfgrecipeproject.controllers;

import edu.vitargo.sfgrecipeproject.commands.IngredientCommand;
import edu.vitargo.sfgrecipeproject.commands.RecipeCommand;
import edu.vitargo.sfgrecipeproject.domain.Ingredient;
import edu.vitargo.sfgrecipeproject.domain.Recipe;
import edu.vitargo.sfgrecipeproject.services.IngredientService;
import edu.vitargo.sfgrecipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.criteria.CriteriaBuilder;

import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    IngredientController ingredientController;

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ingredientController = new IngredientController(recipeService, ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void getIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().is(200))
                .andExpect(view().name("recipe/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void getIngredientsByRecipeIdAndIngredientId() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.getIngredientByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().is(200))
                .andExpect(view().name("recipe/ingredients/show"))
                .andExpect(model().attributeExists("ingredient"));
    }
}