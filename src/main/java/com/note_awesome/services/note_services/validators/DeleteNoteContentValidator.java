package com.note_awesome.services.note_services.validators;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.core.repositories.note.INoteContentRepository;
import com.note_awesome.extensions.Result;
import com.note_awesome.services.note_services.NoteContentAbstractValidator;
import org.springframework.stereotype.Component;
import com.note_awesome.extensions.Error;

@Component
public class DeleteNoteContentValidator extends NoteContentAbstractValidator {
    private final INoteContentRepository noteContentRepository;

    public DeleteNoteContentValidator(INoteContentBasicValidator noteContentBasicValidator, INoteContentRepository noteContentRepository) {
        super(noteContentBasicValidator);
        this.noteContentRepository = noteContentRepository;
    }

    public static class DeleteNoteContentValidatorError {
        public static final Error NOTE_DOES_NOT_EXIST = new Error("Note does not exist", "Note does not exist");
    }

    @Override
    public Result<NoteContent, Error> validate(NoteContent noteContent) {
        var result = super.validate(noteContent);
        if (!result.isSuccess()) {
            return result;
        }
        var noteExist = this.noteContentRepository.existsById(noteContent.getId());
        if (!noteExist) {
            return Result.failure(DeleteNoteContentValidatorError.NOTE_DOES_NOT_EXIST);
        }

        return Result.success(noteContent);

    }
}
