package edu.vitargo.sfgrecipeproject.repositories;

import edu.vitargo.sfgrecipeproject.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
