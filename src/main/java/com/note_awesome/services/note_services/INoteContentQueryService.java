package com.note_awesome.services.note_services;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;

public interface INoteContentQueryService {
    Result<NoteContent, Error> getNoteContentById(Long noteId);
}
