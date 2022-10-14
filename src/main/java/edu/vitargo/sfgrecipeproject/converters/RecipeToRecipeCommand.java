package edu.vitargo.sfgrecipeproject.converters;

import edu.vitargo.sfgrecipeproject.commands.RecipeCommand;
import edu.vitargo.sfgrecipeproject.domain.Category;
import edu.vitargo.sfgrecipeproject.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConvertor;
    private final IngredientToIngredientCommand ingredientConvertor;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConvertor, IngredientToIngredientCommand ingredientConvertor, NotesToNotesCommand notesCommand) {
        this.categoryConvertor = categoryConvertor;
        this.ingredientConvertor = ingredientConvertor;
        this.notesConverter = notesCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null){
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setNotes(notesConverter.convert(source.getNotes()));

        if(source.getCategories() != null && !source.getCategories().isEmpty()){
            source.getCategories()
                    .forEach((Category category) -> recipeCommand.getCategories().add(categoryConvertor.convert(category)));
        }

        if(source.getIngredients() != null && !source.getIngredients().isEmpty()){
            source.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingredientConvertor.convert(ingredient)));
        }

        return recipeCommand;
    }
}
