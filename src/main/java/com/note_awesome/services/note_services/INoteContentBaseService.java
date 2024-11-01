package com.note_awesome.services.note_services;

public interface INoteContentBaseService {
    public ICreateNoteContentService getCreateNoteContentService();

    public INoteContentUpdateService getNoteContentUpdateService();

    public INoteContentQueryService getNoteContentQueryService();
}
