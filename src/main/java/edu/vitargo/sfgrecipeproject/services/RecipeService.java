package edu.vitargo.sfgrecipeproject.services;

import edu.vitargo.sfgrecipeproject.commands.RecipeCommand;
import edu.vitargo.sfgrecipeproject.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getAllRecipe();

    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

}


