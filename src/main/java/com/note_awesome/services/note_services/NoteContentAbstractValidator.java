package com.note_awesome.services.note_services;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import com.note_awesome.services.note_services.validators.INoteContentBasicValidator;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public abstract class NoteContentAbstractValidator implements INoteContentBasicValidator {

    private final INoteContentBasicValidator noteContentBasicValidator;

    public NoteContentAbstractValidator(INoteContentBasicValidator noteContentBasicValidator) {
        this.noteContentBasicValidator = noteContentBasicValidator;
    }

    @Override
    public Result<NoteContent, Error> validate(NoteContent noteContent) {
        return noteContentBasicValidator.validate(noteContent);
    }
}
