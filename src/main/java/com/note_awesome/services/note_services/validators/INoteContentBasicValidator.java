package com.note_awesome.services.note_services.validators;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.IValidator;

public interface INoteContentBasicValidator extends IValidator<NoteContent, Error> {
    @Override
    public Result<NoteContent, Error> validate(NoteContent noteContent);
}
