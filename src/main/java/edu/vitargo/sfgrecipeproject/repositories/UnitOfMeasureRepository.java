package edu.vitargo.sfgrecipeproject.repositories;

import edu.vitargo.sfgrecipeproject.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> getUnitOfMeasureByDescription(String description);
}
