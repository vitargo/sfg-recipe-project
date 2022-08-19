package edu.vitargo.sfgrecipeproject.bootstrap;

import edu.vitargo.sfgrecipeproject.domain.*;
import edu.vitargo.sfgrecipeproject.repositories.CategoryRepository;
import edu.vitargo.sfgrecipeproject.repositories.RecipeRepository;
import edu.vitargo.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.getUnitOfMeasureByDescription("Each");

        if(eachUomOptional.isEmpty()){
            throw new RuntimeException("Each UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.getUnitOfMeasureByDescription("Tablespoon");

        if(tableSpoonUomOptional.isEmpty()){
            throw new RuntimeException("Tablespoon UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.getUnitOfMeasureByDescription("Teaspoon");

        if(teaSpoonUomOptional.isEmpty()){
            throw new RuntimeException("Teaspoon UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.getUnitOfMeasureByDescription("Pint");

        if(pintUomOptional.isEmpty()){
            throw new RuntimeException("Pint UOM Not Found");
        }


        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teapoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();

        Optional<Category> ukrainianCategoryOptional = categoryRepository.getCategoryByDescription("Ukrainian");

        if(ukrainianCategoryOptional.isEmpty()){
            throw new RuntimeException("Ukrainian Category Not Found");
        }

        Category ukrainianCategory = ukrainianCategoryOptional.get();

        /*
        *   DERUNY
        **/
        Recipe derunyRecipe = new Recipe();
        derunyRecipe.setDescription("Deruny (Ukrainian Potato Pancakes)");
        derunyRecipe.setPrepTime(15);
        derunyRecipe.setCookTime(15);
        derunyRecipe.setDifficulty(Difficulty.EASY);
        derunyRecipe.setDirections("1. Grate potatoes and onion\n" +
                "2. Stir in egg and dry ingredients (flour, salt and baking soda)\n" +
                "3. Heat a lightly oiled skillet over medium heat\n" +
                "4. Spoon pancake batter into skillet\n" +
                "5. Fry each side for few minutes until golden brown\n" +
                "6. Serve with sour cream");

        Notes derunyNotes = new Notes();
        derunyNotes.setRecipeNotes("You could also add spinach, green onion etc. instead of onion");

        derunyRecipe.setNotes(derunyNotes);

        derunyRecipe.addIngredient(new Ingredient("Large potatoes", new BigDecimal(4), eachUom));
        derunyRecipe.addIngredient(new Ingredient("Small yellow onion", new BigDecimal(1), eachUom));
        derunyRecipe.addIngredient(new Ingredient("Large egg", new BigDecimal(1), eachUom));
        derunyRecipe.addIngredient(new Ingredient("All-purpose flour", new BigDecimal(6), tableSpoonUom));
        derunyRecipe.addIngredient(new Ingredient("Fine salt", new BigDecimal(2), teapoonUom));
        derunyRecipe.addIngredient(new Ingredient("Grapeseed oil for frying", new BigDecimal(2), tableSpoonUom));
        derunyRecipe.addIngredient(new Ingredient("Sour cream to serve", new BigDecimal(2), tableSpoonUom));

        derunyRecipe.getCategories().add(ukrainianCategory);

        recipes.add(derunyRecipe);

        /*
         *   SYRNYKY
         **/
        Recipe syrnykyRecipe = new Recipe();
        syrnykyRecipe.setDescription("Syrnyky (Ukrainian Cottage Cheese Pancakes)");
        syrnykyRecipe.setCookTime(15);
        syrnykyRecipe.setPrepTime(15);
        syrnykyRecipe.setDifficulty(Difficulty.EASY);

        syrnykyRecipe.setDirections("1. Mix properly cottage cheese, egg, salt and sugar in a large bowl" +
                "2. Add flour and knead the dough. Check the consistency. Probably it will be necessary only 2 tbsp of flour." +
                "3. Make balls from the dough. After add some flour on the plate and cover the balls with flour. Shake off odd flour. Heat the vegetable oil in the pan and fry cheese pancakes. They should have a golden color." +
                "4. Serve with sour cream or jam.");

        Notes syrnykyNotes = new Notes();
        syrnykyNotes.setRecipeNotes("You can also add raisins and serves with a sugar powder");

        syrnykyRecipe.setNotes(syrnykyNotes);

        syrnykyRecipe.addIngredient(new Ingredient("Cottage cheese", new BigDecimal(".5"), pintUom));
        syrnykyRecipe.addIngredient(new Ingredient("Egg", new BigDecimal(1), eachUom));
        syrnykyRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(2), tableSpoonUom));
        syrnykyRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teapoonUom));
        syrnykyRecipe.addIngredient(new Ingredient("All-purpose flour", new BigDecimal(4), tableSpoonUom));

        syrnykyRecipe.getCategories().add(ukrainianCategory);

        recipes.add(syrnykyRecipe);
        return recipes;
    }
}
