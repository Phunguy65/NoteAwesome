package com.note_awesome.services.note_services.validators;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class NoteContentBasicValidator implements INoteContentBasicValidator {
    private final int MAX_LENGTH_TEXT_CONTENT = 255;
    private final int MAX_LENGTH_URL_LOCATION = 255;

    public static class NoteContentValidationError {
        public final static Error NOTE_CONTENT_IS_EMPTY = new Error("Note content is empty", "Note content is empty");
        public final static Error NOTE_CONTENT_TEXT_CONTENT_EMPTY = new Error("Note content text content is empty", "Note content text content is empty");
        public final static Error NOTE_CONTENT_TEXT_CONTENT_TOO_LONG = new Error("Note content text content is too long", "Note content text content is too long");

        public static Error URL_LOCATION_NOT_FOUND(String imageUrl) {
            return new Error("URL location not found", "URL location not found: " + imageUrl);
        }

        public static Error TITLE_TOO_LONG = new Error("Title is too long", "Title is too long");
        public static Error RAW_CONTENT_EMPTY = new Error("Raw content is empty", "Raw content is empty");
    }

    @Override
    public Result<NoteContent, Error> validate(NoteContent noteContent) {
        if (noteContentIsNull(noteContent)) {
            return Result.failure(NoteContentValidationError.NOTE_CONTENT_IS_EMPTY);
        }
        if (textContentIsNull(noteContent)) {
            return Result.failure(NoteContentValidationError.NOTE_CONTENT_TEXT_CONTENT_EMPTY);
        }
        if (textContentIsTooLong(noteContent)) {
            return Result.failure(NoteContentValidationError.TITLE_TOO_LONG);
        }
        if (rawContentIsNull(noteContent)) {
            return Result.failure(NoteContentValidationError.RAW_CONTENT_EMPTY);
        }
        if (titleIsTooLong(noteContent)) {
            return Result.failure(NoteContentValidationError.TITLE_TOO_LONG);
        }
        if (!locationIsExist(noteContent)) {
            return Result.failure(NoteContentValidationError.URL_LOCATION_NOT_FOUND(noteContent.getNoteLocation()));
        }
        return Result.success(noteContent);
    }

    protected final boolean noteContentIsNull(NoteContent noteContent) {
        return noteContent == null;
    }

    protected final boolean textContentIsNull(NoteContent noteContent) {
        return noteContent.getTextContent() == null;
    }

    protected final boolean locationIsExist(NoteContent noteContent) {
        /* TODO: workaround for the original code*/
//        if (!noteContent.getNoteLocation().isEmpty()) {
//            return Files.exists(Path.of(noteContent.getNoteLocation()));
//        }
        return true;
    }

    protected final boolean locationIsTooLong(NoteContent noteContent) {
        return noteContent.getNoteLocation().length() > MAX_LENGTH_URL_LOCATION;
    }

    protected final boolean textContentIsTooLong(NoteContent noteContent) {
        return noteContent.getTextContent().length() > MAX_LENGTH_TEXT_CONTENT;
    }

    protected final boolean titleIsTooLong(NoteContent noteContent) {
        return noteContent.getTitle().length() > MAX_LENGTH_TEXT_CONTENT;
    }

    protected final boolean rawContentIsNull(NoteContent noteContent) {
        return noteContent.getRawContent() == null;
    }
}