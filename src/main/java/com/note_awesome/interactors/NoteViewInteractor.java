package com.note_awesome.interactors;


import com.note_awesome.models.ISessionViewModel;
import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.services.note_services.INoteContentBaseService;
import org.springframework.stereotype.Component;

@Component
public class NoteViewInteractor {
    private final INoteContentBaseService noteContentBaseService;
    private final NoteViewModel noteViewModel;
    private final ISessionViewModel sessionViewModel;

    public NoteViewInteractor(INoteContentBaseService noteContentBaseService, NoteViewModel noteViewModel, ISessionViewModel sessionViewModel) {
        this.noteContentBaseService = noteContentBaseService;
        this.noteViewModel = noteViewModel;
        this.sessionViewModel = sessionViewModel;
    }
}
