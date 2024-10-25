package com.note_awesome.services.note_services;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;

public interface ICreateNoteContentService {
    public Result<NoteContent, Error> create(NoteContent noteContent);

    public Result<NoteContent, Error> create(NoteContent noteContent, long userProfileId);

    public Result<NoteContent, Error> create(NoteContent noteContent, long userProfileId, long tagId);
}
