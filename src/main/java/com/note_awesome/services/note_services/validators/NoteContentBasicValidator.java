package com.note_awesome.services.note_services.validators;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class NoteContentBasicValidator implements INoteContentBasicValidator {
    private final int MAX_LENGTH_TEXT_CONTENT = 1000;
    private final int MAX_LENGTH_URL_LOCATION = 255;

    public static class NoteContentValidationError {
        public final static Error NOTE_CONTENT_IS_EMPTY = new Error("Note content is empty", "Note content is empty");
        public final static Error NOTE_CONTENT_TEXT_CONTENT_EMPTY = new Error("Note content text content is empty", "Note content text content is empty");
        public final static Error URL_LOCATION_EMPTY = new Error("URL location is empty", "URL location is empty");
        public final static Error NOTE_CONTENT_TEXT_CONTENT_TOO_LONG = new Error("Note content text content is too long", "Note content text content is too long");
        public final static Error URL_LOCATION_TOO_LONG = new Error("URL location is too long", "URL location is too long");

        public static Error URL_LOCATION_NOT_FOUND(String imageUrl) {
            return new Error("URL location not found", "URL location not found: " + imageUrl);
        }
    }

    @Override
    public Result<NoteContent, Error> validate(NoteContent noteContent) {
        if (noteContentIsNull(noteContent)) {
            return Result.failure(NoteContentValidationError.NOTE_CONTENT_IS_EMPTY);
        }
        if (textContentIsEmpty(noteContent)) {
            return Result.failure(NoteContentValidationError.NOTE_CONTENT_TEXT_CONTENT_EMPTY);
        }
        if (textContentTooLong(noteContent)) {
            return Result.failure(NoteContentValidationError.NOTE_CONTENT_TEXT_CONTENT_TOO_LONG);
        }
        if (isUrlLocationNull(noteContent)) {
            return Result.failure(NoteContentValidationError.URL_LOCATION_NOT_FOUND(noteContent.getUrlLocation()));
        }
        return Result.success(noteContent);
    }

    protected final boolean noteContentIsNull(NoteContent noteContent) {
        return noteContent == null;
    }

    protected final boolean textContentIsEmpty(NoteContent noteContent) {
        return noteContent.getTextContent() == null || noteContent.getTextContent().isEmpty() || noteContent.getTextContent().length() > MAX_LENGTH_TEXT_CONTENT;
    }

    protected final boolean isUrlLocationNull(NoteContent noteContent) {
        return noteContent.getUrlLocation() == null;
    }

    protected final boolean isUrlLocationTooLong(NoteContent noteContent) {
        return noteContent.getUrlLocation().length() > MAX_LENGTH_URL_LOCATION;
    }

    protected final boolean textContentTooLong(NoteContent noteContent) {
        return noteContent.getTextContent().length() > MAX_LENGTH_TEXT_CONTENT;
    }


}