package com.note_awesome.controllers;

import com.note_awesome.interactors.NoteViewInteractor;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.models.ViewStateViewModel;
import com.note_awesome.views.note_views.NoteViewBuilder;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class NoteViewController {
    private final NoteViewBuilder noteViewBuilder;
    private final NoteViewInteractor noteViewInteractor;
    private final NoteViewModel noteViewModel;
    private final ViewStateViewModel viewStateViewModel;

    public NoteViewController(NoteViewInteractor noteViewInteractor, NoteViewModel noteViewModel, ViewStateViewModel viewStateViewModel) {
        this.noteViewInteractor = noteViewInteractor;
        this.noteViewModel = noteViewModel;
        this.viewStateViewModel = viewStateViewModel;
        this.noteViewBuilder = new NoteViewBuilder(this.noteViewModel, this::createNote, this::updateNote, this::closeNoteEditor, this::showUpdateNoteEditor, this::switchNoteBoard, this::deleteNote);
        load();
    }

    private void load() {
        this.viewStateViewModel.showNoteViewProperty().subscribe(val -> {
            if (val) {
                this.noteViewInteractor.refreshNoteBoard();
            }
        });
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

    private void deleteNote(Long noteId, Boolean pinned) {
        if (pinned) {
            noteViewInteractor.deletePinnedNote(noteId);
        } else {
            noteViewInteractor.deleteUnpinnedNote(noteId);
        }
        noteViewInteractor.updateNoteBoard();
    }


    public Region getView() {
        return noteViewBuilder.build();
    }
}
