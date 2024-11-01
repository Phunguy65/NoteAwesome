package com.note_awesome.services.note_services;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;

import java.util.List;

public interface INoteContentUpdateService {

    Result<NoteContent, Error> update(long noteId, String title, String content, boolean pinned, List<Byte> rawContent);

    Result<NoteContent, Error> update(long noteId, String title, String content, boolean pinned, byte[] rawContent);

    Result<NoteContent, Error> update(long noteId, boolean pinned);
}
