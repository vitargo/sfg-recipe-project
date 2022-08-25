package edu.vitargo.sfgrecipeproject.services;

import edu.vitargo.sfgrecipeproject.domain.Recipe;
import edu.vitargo.sfgrecipeproject.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getAllRecipe() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);

        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> result = recipeService.getAllRecipe();

        Assert.assertEquals(recipes.size(), result.size());
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }
}