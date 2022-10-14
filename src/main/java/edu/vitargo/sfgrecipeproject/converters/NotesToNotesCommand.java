package edu.vitargo.sfgrecipeproject.converters;

import edu.vitargo.sfgrecipeproject.commands.NotesCommand;
import edu.vitargo.sfgrecipeproject.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {
    @Override
    public NotesCommand convert(Notes source) {
        if(source == null){
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}
