package edu.vitargo.sfgrecipeproject.services;

import edu.vitargo.sfgrecipeproject.commands.UnitOfMeasureCommand;
import edu.vitargo.sfgrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import edu.vitargo.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository uomRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand uomToUomCommandConverter;

    public UnitOfMeasureImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand uomToUomCommandConverter) {
        this.uomRepository = uomRepository;
        this.uomToUomCommandConverter = uomToUomCommandConverter;
    }

    @Override
    public Set<UnitOfMeasureCommand> getAllUom() {

        return StreamSupport.stream(uomRepository.findAll().spliterator(), false)
                .map(uomToUomCommandConverter::convert)
                .collect(Collectors.toSet());
    }
}
