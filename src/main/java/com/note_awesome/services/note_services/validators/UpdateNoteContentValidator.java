package com.note_awesome.services.note_services.validators;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.core.repositories.note.INoteContentRepository;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import com.note_awesome.services.note_services.NoteContentAbstractValidator;
import org.springframework.stereotype.Component;

@Component
public class UpdateNoteContentValidator extends NoteContentAbstractValidator {
    private final INoteContentRepository noteContentRepository;

    public static class UpdateNoteContentValidatorError {
        public static final Error NOTE_DOES_NOT_EXIST = new Error("Note does not exist", "Note does not exist");
    }

    public UpdateNoteContentValidator(INoteContentBasicValidator noteContentBasicValidator, INoteContentRepository noteContentRepository) {
        super(noteContentBasicValidator);
        this.noteContentRepository = noteContentRepository;
    }

    @Override
    public Result<NoteContent, Error> validate(NoteContent noteContent) {
        var result = super.validate(noteContent);
        if (!result.isSuccess()) {
            return result;
        }
        var noteExist = this.noteContentRepository.existsById(noteContent.getId());
        if (!noteExist) {
            return Result.failure(UpdateNoteContentValidatorError.NOTE_DOES_NOT_EXIST);
        }
        return Result.success(noteContent);
    }
}
