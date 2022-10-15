package edu.vitargo.sfgrecipeproject.services;

import edu.vitargo.sfgrecipeproject.commands.IngredientCommand;
import edu.vitargo.sfgrecipeproject.converters.IngredientCommandToIngredient;
import edu.vitargo.sfgrecipeproject.converters.IngredientToIngredientCommand;
import edu.vitargo.sfgrecipeproject.domain.Recipe;
import edu.vitargo.sfgrecipeproject.repositories.IngredientRepository;
import edu.vitargo.sfgrecipeproject.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommandConverter;
    private final IngredientCommandToIngredient ingredientCommandToIngredientConverter;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommandConverter, IngredientCommandToIngredient ingredientCommandToIngredientConverter) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommandConverter = ingredientToIngredientCommandConverter;
        this.ingredientCommandToIngredientConverter = ingredientCommandToIngredientConverter;
    }

    @Override
    public IngredientCommand getIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(recipe.isPresent()){
            Optional<IngredientCommand> ingredient = recipe.get().getIngredients().stream()
                    .filter(i -> i.getId().equals(ingredientId))
                    .map(ingredientToIngredientCommandConverter::convert)
                    .findFirst();
            if(ingredient.isPresent()){
                return ingredient.get();
            }
        }
        return null;
    }
}
