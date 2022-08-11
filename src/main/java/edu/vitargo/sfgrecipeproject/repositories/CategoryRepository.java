package edu.vitargo.sfgrecipeproject.repositories;

import edu.vitargo.sfgrecipeproject.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> getCategoryByDescription(String description);
}
