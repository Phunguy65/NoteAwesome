package com.note_awesome.controllers;

import com.note_awesome.interactors.NoteViewInteractor;
import com.note_awesome.models.NoteViewModel;
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
        this.noteViewBuilder = new NoteViewBuilder(this.noteViewModel, this::createNote, this::updateNote, this::closeNoteEditor, this::showUpdateNoteEditor, this::switchNoteBoard);
    }

    private void createNote(Runnable postCreateNote) {
        noteViewInteractor.createNote();
        noteViewInteractor.updateNoteBoard();
        postCreateNote.run();
    }


    private void updateNoteBoard() {
        noteViewInteractor.updateNoteBoard();
    }


    private void closeNoteEditor(Runnable postCloseNoteEditor) {
        noteViewInteractor.closeNoteEditor();
        postCloseNoteEditor.run();
    }

    private boolean showUpdateNoteEditor(Long id) {
        return noteViewInteractor.showUpdateNoteEditor(id);
    }

    private void updateNote(Runnable postUpdateNote) {
        noteViewInteractor.updateNote();
        noteViewInteractor.updateNoteBoard();
        postUpdateNote.run();
    }

    private void switchNoteBoard(Long id, boolean newVal) {
        noteViewInteractor.switchNoteBoard(id, newVal);
    }


    public Region getView() {
        return noteViewBuilder.build();
    }
}
