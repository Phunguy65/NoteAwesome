package com.note_awesome.interactors;


import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.services.note_services.INoteContentBaseService;
import org.springframework.stereotype.Component;

@Component
public class NoteViewInteractor {
    private final INoteContentBaseService noteContentBaseService;
    private final NoteViewModel noteViewModel;

    public NoteViewInteractor(INoteContentBaseService noteContentBaseService, NoteViewModel noteViewModel) {
        this.noteContentBaseService = noteContentBaseService;
        this.noteViewModel = noteViewModel;
    }
}
