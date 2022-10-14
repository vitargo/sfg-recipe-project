package edu.vitargo.sfgrecipeproject.converters;

import edu.vitargo.sfgrecipeproject.commands.RecipeCommand;
import edu.vitargo.sfgrecipeproject.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConvertor;
    private final IngredientCommandToIngredient ingredientConvertor;
    private final NotesCommandToNotes notesConvertor;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConvertor, IngredientCommandToIngredient ingredientConvertor, NotesCommandToNotes notesConvertor) {
        this.categoryConvertor = categoryConvertor;
        this.ingredientConvertor = ingredientConvertor;
        this.notesConvertor = notesConvertor;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(Objects.requireNonNull(notesConvertor.convert(source.getNotes())));

        if(source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryConvertor.convert(category)));
        }

        if(source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConvertor.convert(ingredient)));
        }
        return recipe;
    }
}
