package com.note_awesome.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class NoteViewModel {

    NoteEditorViewModel noteEditorViewModel;

    ObservableList<NoteCardViewModel> pinnedNotes;
    ObservableList<NoteCardViewModel> unpinnedNotes;

    public NoteViewModel() {
        noteEditorViewModel = new NoteEditorViewModel();
        pinnedNotes = FXCollections.observableArrayList();
        unpinnedNotes = FXCollections.observableArrayList();
    }

    public NoteViewModel(NoteEditorViewModel noteEditorViewModel, ObservableList<NoteCardViewModel> pinnedNotes, ObservableList<NoteCardViewModel> unpinnedNotes) {
        this.noteEditorViewModel = noteEditorViewModel;
        this.pinnedNotes = pinnedNotes;
        this.unpinnedNotes = unpinnedNotes;
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

}
