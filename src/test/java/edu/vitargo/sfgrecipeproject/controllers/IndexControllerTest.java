package edu.vitargo.sfgrecipeproject.controllers;

import edu.vitargo.sfgrecipeproject.domain.Recipe;
import edu.vitargo.sfgrecipeproject.repositories.CategoryRepository;
import edu.vitargo.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import edu.vitargo.sfgrecipeproject.services.RecipeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    private IndexController controller;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Captor
    ArgumentCaptor<Set> set;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new IndexController(categoryRepository, unitOfMeasureRepository, recipeService);
    }

    @Test
    public void testMVC() throws Exception {
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndex() {
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipes.add(recipe1);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipes.add(recipe2);
        String excpected = "index";

        Mockito.when(recipeService.getAllRecipe()).thenReturn(recipes);

        String actual = controller.getIndex(model);

        Assert.assertEquals(excpected, actual);
        Mockito.verify(model, times(1)).addAttribute(eq("recipes"), set.capture());
        Set<Recipe> capturedSet = set.getValue();
        Mockito.verify(recipeService, times(1)).getAllRecipe();
        Assert.assertEquals(recipes, capturedSet);
    }
}