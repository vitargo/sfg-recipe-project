package edu.vitargo.sfgrecipeproject.repositories;


import edu.vitargo.sfgrecipeproject.domain.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository repository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getUnitOfMeasureByDescriptionTeaSpoon() {
        String uom = "Teaspoon";
        Optional<UnitOfMeasure> result = repository.getUnitOfMeasureByDescription(uom);

        Assert.assertEquals(uom, result.orElseGet(UnitOfMeasure::new).getDescription());
    }

    @Test
    public void getUnitOfMeasureByDescriptionCup() {
        String uom = "Cup";
        Optional<UnitOfMeasure> result = repository.getUnitOfMeasureByDescription(uom);

        Assert.assertEquals(uom, result.orElseGet(UnitOfMeasure::new).getDescription());
    }
}