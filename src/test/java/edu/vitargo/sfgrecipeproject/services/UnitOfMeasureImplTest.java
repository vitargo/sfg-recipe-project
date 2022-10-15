package edu.vitargo.sfgrecipeproject.services;

import edu.vitargo.sfgrecipeproject.commands.UnitOfMeasureCommand;
import edu.vitargo.sfgrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import edu.vitargo.sfgrecipeproject.domain.UnitOfMeasure;
import edu.vitargo.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureImplTest {

    UnitOfMeasureService uomService;

    @Mock
    UnitOfMeasureRepository uomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        uomService = new UnitOfMeasureImpl(uomRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void getAllUom() {
        Set<UnitOfMeasure> units = new HashSet<>();
        UnitOfMeasure cup = UnitOfMeasure.builder().id(1L).description("Cup").build();
        UnitOfMeasure spoon = UnitOfMeasure.builder().id(2L).description("Spoon").build();
        units.add(cup);
        units.add(spoon);

        when(uomRepository.findAll()).thenReturn(units);

        Set<UnitOfMeasureCommand> result = uomService.getAllUom();

        assertEquals(2, result.size());

        verify(uomRepository, times(1)).findAll();
    }
}