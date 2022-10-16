package edu.vitargo.sfgrecipeproject.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureCommand {
    private Long id;
    private String description;

    @Builder
    public UnitOfMeasureCommand(Long id) {
        this.id = id;
    }
}
