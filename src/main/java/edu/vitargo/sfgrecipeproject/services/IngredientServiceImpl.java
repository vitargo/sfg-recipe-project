package edu.vitargo.sfgrecipeproject.services;

import edu.vitargo.sfgrecipeproject.commands.IngredientCommand;
import edu.vitargo.sfgrecipeproject.converters.IngredientCommandToIngredient;
import edu.vitargo.sfgrecipeproject.converters.IngredientToIngredientCommand;
import edu.vitargo.sfgrecipeproject.domain.Ingredient;
import edu.vitargo.sfgrecipeproject.domain.Recipe;
import edu.vitargo.sfgrecipeproject.exception.RecipeException;
import edu.vitargo.sfgrecipeproject.repositories.IngredientRepository;
import edu.vitargo.sfgrecipeproject.repositories.RecipeRepository;
import edu.vitargo.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommandConverter;
    private final IngredientCommandToIngredient ingredientCommandToIngredientConverter;
    private final UnitOfMeasureRepository uomRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommandConverter, IngredientCommandToIngredient ingredientCommandToIngredientConverter, UnitOfMeasureRepository uomRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommandConverter = ingredientToIngredientCommandConverter;
        this.ingredientCommandToIngredientConverter = ingredientCommandToIngredientConverter;
        this.uomRepository = uomRepository;
    }

    @Override
    public IngredientCommand getIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (recipe.isPresent()) {
            Optional<IngredientCommand> ingredient = recipe.get().getIngredients().stream()
                    .filter(i -> i.getId().equals(ingredientId))
                    .map(ingredientToIngredientCommandConverter::convert)
                    .findFirst();
            if (ingredient.isPresent()) {
                return ingredient.get();
            }
        }
        return null;
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(command.getRecipeId());

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                    .filter(i -> i.getId().equals(command.getId()))
                    .findFirst();
            if (optionalIngredient.isPresent()) {
                Ingredient ingredient = optionalIngredient.get();
                ingredient.setDescription(command.getDescription());
                ingredient.setAmount(command.getAmount());
                ingredient.setUom(uomRepository.findById(command.getUnitOfMeasureCommand().getId()).orElseThrow(() -> new RecipeException("Not Fount Unit of measure!")));
            } else {
                Ingredient ingredient = ingredientCommandToIngredientConverter.convert(command);
                if (ingredient != null) {
                    ingredient.setRecipe(recipe);
                    recipe.addIngredient(ingredient);
                }
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> saveOptionalIngredient;
            if(command.getId() != null) {
                saveOptionalIngredient = savedRecipe.getIngredients().stream()
                        .filter(i -> i.getId().equals(command.getId()))
                        .findFirst();
            } else {
                saveOptionalIngredient = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUnitOfMeasureCommand().getId()))
                        .findFirst();
            }
            if(saveOptionalIngredient.isPresent()){
                return ingredientToIngredientCommandConverter.convert(saveOptionalIngredient.get());
            }
        }
        return new IngredientCommand();
    }

    @Override
    public void deleteIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredient = recipe.getIngredients().stream()
                    .filter(i -> i.getId().equals(ingredientId))
                    .findFirst();

            if(ingredient.isPresent()){
                Ingredient ingredientToDelete = ingredient.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientToDelete);
                recipeRepository.save(recipe);
            }
        }
    }
}
