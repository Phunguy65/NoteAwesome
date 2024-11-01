package com.note_awesome.services.note_services;

import org.springframework.stereotype.Component;

@Component
public class NoteContentService implements INoteContentBaseService {
    private final ICreateNoteContentService createNoteContentService;
    private final INoteContentUpdateService noteContentUpdateService;
    private final INoteContentQueryService noteContentQueryService;

    public NoteContentService(ICreateNoteContentService createNoteContentService, INoteContentUpdateService noteContentUpdateService, INoteContentQueryService noteContentQueryService) {
        this.createNoteContentService = createNoteContentService;
        this.noteContentUpdateService = noteContentUpdateService;
        this.noteContentQueryService = noteContentQueryService;
    }

    @Override
    public ICreateNoteContentService getCreateNoteContentService() {
        return createNoteContentService;
    }

    @Override
    public INoteContentUpdateService getNoteContentUpdateService() {
        return noteContentUpdateService;
    }

    @Override
    public INoteContentQueryService getNoteContentQueryService() {
        return noteContentQueryService;
    }
}
