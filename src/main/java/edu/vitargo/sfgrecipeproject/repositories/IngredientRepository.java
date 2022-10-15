package edu.vitargo.sfgrecipeproject.repositories;

import edu.vitargo.sfgrecipeproject.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
