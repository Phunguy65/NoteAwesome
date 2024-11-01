package com.note_awesome.services.note_services;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.core.repositories.note.INoteContentRepository;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;
import com.note_awesome.services.note_services.validators.UpdateNoteContentValidator;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteContentUpdateService implements INoteContentUpdateService {

    private final INoteContentRepository noteContentRepository;

    private final NoteContentAbstractValidator noteContentAbstractValidator;

    public NoteContentUpdateService(INoteContentRepository noteContentRepository, UpdateNoteContentValidator noteContentAbstractValidator) {
        this.noteContentRepository = noteContentRepository;
        this.noteContentAbstractValidator = noteContentAbstractValidator;
    }

    private Result<NoteContent, Error> save(NoteContent noteContent) {
        try {
            var savedNoteContent = this.noteContentRepository.save(noteContent);
            return Result.success(savedNoteContent);
        } catch (Exception e) {
            return Result.failure(new Error("Failed to save note content", e.getMessage()));
        }
    }

    private Result<NoteContent, Error> procedure(NoteContent noteContent) {
        var validationResult = this.noteContentAbstractValidator.validate(noteContent);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }
        return Result.success(noteContent);
    }

    private Result<NoteContent, Error> fetch(long noteId) {
        var noteContent = this.noteContentRepository.findById(noteId);
        return noteContent.<Result<NoteContent, Error>>map(Result::success).orElseGet(() -> Result.failure(new Error("Note does not exist", "Note does not exist")));
    }


    private Result<NoteContent, Error> update(NoteContent noteContent) {
        return procedure(noteContent).Match(this::save, Result::failure);
    }

    @Override
    public Result<NoteContent, Error> update(long noteId, String title, String content, boolean pinned, byte[] rawContent) {
        var fetchedNoteContent = fetch(noteId);

        if (fetchedNoteContent.isSuccess()) {
            var noteContent = fetchedNoteContent.getValue();
            noteContent.setTitle(title);
            noteContent.setTextContent(content);
            noteContent.setRawContent(rawContent);
            noteContent.setPinned(pinned);
            return procedure(noteContent).Match(note -> {
                this.noteContentRepository.update(note.getTitle(), note.getTextContent(), note.getRawContent(), note.pinned(), note.getId());
                return Result.success(note);
            }, Result::failure);
        }

        return Result.failure(new Error("Note does not exist", "Note does not exist"));

    }

    @Override
    public Result<NoteContent, Error> update(long noteId, String title, String content, boolean pinned, List<Byte> rawContent) {
        return update(noteId, title, content, pinned, ArrayUtils.toPrimitive(rawContent.toArray(new Byte[0])));
    }

    @Override
    public Result<NoteContent, Error> update(long noteId, boolean pinned) {
        var fetchedNoteContent = fetch(noteId);

        if (fetchedNoteContent.isSuccess()) {
            var noteContent = fetchedNoteContent.getValue();
            noteContent.setPinned(pinned);
            return update(noteContent);
        }

        return Result.failure(new Error("Note does not exist", "Note does not exist"));
    }


}

