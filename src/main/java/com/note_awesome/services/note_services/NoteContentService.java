package com.note_awesome.services.note_services;

import org.springframework.stereotype.Component;

@Component
public class NoteContentService implements INoteContentBaseService {
    private final ICreateNoteContentService createNoteContentService;

    public NoteContentService(ICreateNoteContentService createNoteContentService) {
        this.createNoteContentService = createNoteContentService;
    }

    @Override
    public ICreateNoteContentService getCreateNoteContentService() {
        return createNoteContentService;
    }
}
