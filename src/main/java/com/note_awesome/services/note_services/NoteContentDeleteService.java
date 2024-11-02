package com.note_awesome.services.note_services;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.core.repositories.note.INoteContentRepository;
import com.note_awesome.extensions.Result;
import com.note_awesome.services.note_services.validators.DeleteNoteContentValidator;
import com.note_awesome.services.note_services.validators.NoteContentBasicValidator;
import com.note_awesome.extensions.Error;
import org.springframework.stereotype.Component;

@Component
public class NoteContentDeleteService implements INoteContentDeleteService {
    private final INoteContentRepository noteContentRepository;
    private final DeleteNoteContentValidator deleteNoteContentValidator;

    public NoteContentDeleteService(INoteContentRepository noteContentRepository, DeleteNoteContentValidator deleteNoteContentValidator) {
        this.noteContentRepository = noteContentRepository;
        this.deleteNoteContentValidator = deleteNoteContentValidator;
    }

    private Result<NoteContent, Error> procedure(NoteContent noteContent) {
        var validationResult = this.deleteNoteContentValidator.validate(noteContent);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }
        return Result.success(noteContent);
    }

    private Result<NoteContent, Error> fetch(long noteId) {
        var noteContent = this.noteContentRepository.findById(noteId);
        return noteContent.<Result<NoteContent, Error>>map(Result::success).orElseGet(() -> Result.failure(new Error("Note does not exist", "Note does not exist")));
    }

    @Override
    public Result<NoteContent, Error> markAsDeleted(long noteId) {
        var fetchedNoteContent = fetch(noteId);
        if (!fetchedNoteContent.isSuccess()) {
            return fetchedNoteContent;
        }

        var noteContent = fetchedNoteContent.getValue();
        noteContent.setDeleted(true);

        this.noteContentRepository.updateDeletedById(true, noteId);

        return Result.success(noteContent);
    }

}
