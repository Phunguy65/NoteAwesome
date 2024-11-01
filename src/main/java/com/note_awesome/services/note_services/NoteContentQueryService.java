package com.note_awesome.services.note_services;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.core.repositories.note.INoteContentRepository;
import com.note_awesome.core.repositories.note.INoteImageRepository;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;
import org.springframework.stereotype.Component;

@Component
public class NoteContentQueryService implements INoteContentQueryService {
    private final INoteContentRepository noteContentRepository;
    private final INoteImageRepository noteImageRepository;

    public NoteContentQueryService(INoteContentRepository noteContentRepository, INoteImageRepository noteImageRepository) {
        this.noteContentRepository = noteContentRepository;
        this.noteImageRepository = noteImageRepository;
    }

    @Override
    public Result<NoteContent, Error> getNoteContentById(Long noteId) {
        var noteContent = this.noteContentRepository.findById(noteId);
        if (noteContent.isEmpty()) {
            return Result.failure(new Error("Note content not found", "note_content_not_found"));
        } else {
            var noteImages = this.noteImageRepository.findByNoteContent_IdAndUsing(noteId, true);
            noteContent.get().setNoteImages(noteImages);
        }
        return Result.success(noteContent.get());
    }
}