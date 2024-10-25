package com.note_awesome.controllers;

import com.note_awesome.interactors.NoteViewInteractor;
import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.services.note_services.INoteContentBaseService;
import com.note_awesome.views.note_views.NoteViewBuilder;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class NoteViewController {
    private final NoteViewBuilder noteViewBuilder;
    private final NoteViewInteractor noteViewInteractor;
    private final NoteViewModel noteViewModel;

    public NoteViewController(NoteViewInteractor noteViewInteractor, NoteViewModel noteViewModel) {
        this.noteViewInteractor = noteViewInteractor;
        this.noteViewModel = noteViewModel;
        this.noteViewBuilder = new NoteViewBuilder(this.noteViewModel);
    }

    public Region getView() {
        return noteViewBuilder.build();
    }
}
