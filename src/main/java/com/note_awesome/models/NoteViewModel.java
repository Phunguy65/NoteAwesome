package com.note_awesome.models;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class NoteViewModel {

    NoteEditorViewModel noteEditorViewModel;

    ObservableList<NoteCardViewModel> pinnedNotes;
    ObservableList<NoteCardViewModel> unpinnedNotes;

    BooleanProperty showNoteEditor;


    public BooleanBinding showUnpinnedNotesProperty() {
        return Bindings.createBooleanBinding(() -> !this.unpinnedNotes.isEmpty(), this.unpinnedNotes);
    }


    public BooleanBinding showPinNotesProperty() {
        return Bindings.createBooleanBinding(() -> !this.pinnedNotes.isEmpty(), this.pinnedNotes);
    }


    BooleanProperty showAllNotes;

    public NoteViewModel() {
        noteEditorViewModel = new NoteEditorViewModel();
        pinnedNotes = FXCollections.observableArrayList();
        unpinnedNotes = FXCollections.observableArrayList();
        showNoteEditor = new SimpleBooleanProperty(false);
        showAllNotes = new SimpleBooleanProperty(false);
    }

    public NoteViewModel(NoteEditorViewModel noteEditorViewModel, ObservableList<NoteCardViewModel> pinnedNotes, ObservableList<NoteCardViewModel> unpinnedNotes) {
        this.noteEditorViewModel = noteEditorViewModel;
        this.pinnedNotes = pinnedNotes;
        this.unpinnedNotes = unpinnedNotes;
        showNoteEditor = new SimpleBooleanProperty(false);
    }

    public NoteEditorViewModel getNoteEditorViewModel() {
        return noteEditorViewModel;
    }

    public void setNoteEditorViewModel(NoteEditorViewModel noteEditorViewModel) {
        this.noteEditorViewModel = noteEditorViewModel;
    }

    public ObservableList<NoteCardViewModel> getPinnedNotes() {
        return pinnedNotes;
    }

    public void setPinnedNotes(ObservableList<NoteCardViewModel> pinnedNotes) {
        this.pinnedNotes = pinnedNotes;
    }

    public ObservableList<NoteCardViewModel> getUnpinnedNotes() {
        return unpinnedNotes;
    }

    public void setUnpinnedNotes(ObservableList<NoteCardViewModel> unpinnedNotes) {
        this.unpinnedNotes = unpinnedNotes;
    }

    public BooleanBinding showAllNotesProperty() {
        return Bindings.createBooleanBinding(() -> pinnedNotes.isEmpty() && unpinnedNotes.isEmpty(), pinnedNotes, unpinnedNotes);
    }

    public BooleanProperty showNoteEditorProperty() {
        return showNoteEditor;
    }


}
