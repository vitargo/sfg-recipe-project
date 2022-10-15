package edu.vitargo.sfgrecipeproject.services;

import edu.vitargo.sfgrecipeproject.commands.IngredientCommand;
import edu.vitargo.sfgrecipeproject.converters.IngredientCommandToIngredient;
import edu.vitargo.sfgrecipeproject.converters.IngredientToIngredientCommand;
import edu.vitargo.sfgrecipeproject.converters.UnitOfMeasureCommandToUnitOfMeasure;
import edu.vitargo.sfgrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import edu.vitargo.sfgrecipeproject.domain.Ingredient;
import edu.vitargo.sfgrecipeproject.domain.Recipe;
import edu.vitargo.sfgrecipeproject.repositories.IngredientRepository;
import edu.vitargo.sfgrecipeproject.repositories.RecipeRepository;
import edu.vitargo.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.webservices.server.AutoConfigureMockWebServiceClient;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private IngredientService ingredientService;

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository uomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        IngredientToIngredientCommand ingredientToIngredientCommandConverter  = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        IngredientCommandToIngredient ingredientCommandToIngredientConverter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());


        ingredientService = new IngredientServiceImpl(ingredientRepository, recipeRepository, ingredientToIngredientCommandConverter, ingredientCommandToIngredientConverter, uomRepository);
    }

    @Test
    void getIngredientByRecipeIdAndIngredientId() {
        Recipe recipe = Recipe.builder().id(1L).description("Test").build();
        Ingredient ingredient1 = Ingredient.builder().id(1L).description("Ingredient 1").build();
        Ingredient ingredient2 = Ingredient.builder().id(2L).description("Ingredient 2").build();
        Ingredient ingredient3 = Ingredient.builder().id(3L).description("Ingredient 3").build();
        Set<Ingredient> ingredients = recipe.getIngredients();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        IngredientCommand result = ingredientService.getIngredientByRecipeIdAndIngredientId(1L, 3L);

        assertEquals(3L, result.getId());
        assertEquals("Ingredient 3", result.getDescription());

        verify(recipeRepository, times(1)).findById(any());
    }
}