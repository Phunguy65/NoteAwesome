package com.note_awesome.services.note_services;

import org.springframework.stereotype.Component;

@Component
public class NoteContentService implements INoteContentBaseService {
    private final ICreateNoteContentService createNoteContentService;
    private final INoteContentUpdateService updateNoteContentService;
    private final INoteContentQueryService noteContentQueryService;
    private final INoteContentDeleteService noteContentDeleteService;

    public NoteContentService(ICreateNoteContentService createNoteContentService, INoteContentUpdateService noteContentUpdateService, INoteContentQueryService noteContentQueryService, INoteContentDeleteService noteContentDeleteService) {
        this.createNoteContentService = createNoteContentService;
        this.updateNoteContentService = noteContentUpdateService;
        this.noteContentQueryService = noteContentQueryService;
        this.noteContentDeleteService = noteContentDeleteService;
    }

    @Override
    public ICreateNoteContentService getCreateNoteContentService() {
        return createNoteContentService;
    }

    @Override
    public INoteContentUpdateService getUpdateNoteContentService() {
        return updateNoteContentService;
    }

    @Override
    public INoteContentQueryService getNoteContentQueryService() {
        return noteContentQueryService;
    }

    @Override
    public INoteContentDeleteService getDeleteNoteContentService() {
        return noteContentDeleteService;
    }

}
