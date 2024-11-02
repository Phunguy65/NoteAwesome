package com.note_awesome.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.stereotype.Component;

@Component
public class ViewStateViewModel {

    private BooleanProperty showNoteView;

    public BooleanProperty showNoteViewProperty() {
        return showNoteView;
    }

    public void setShowNoteView(boolean showNoteView) {
        this.showNoteView.set(showNoteView);
    }

    public boolean isShowNoteView() {
        return showNoteView.get();
    }

    public ViewStateViewModel(BooleanProperty showNoteView) {
        this.showNoteView = showNoteView;
    }

    public ViewStateViewModel() {
        this.showNoteView = new SimpleBooleanProperty();
    }
}
