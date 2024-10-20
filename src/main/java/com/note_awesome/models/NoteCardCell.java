package com.note_awesome.models;

import com.note_awesome.views.note_views.NoteCardFxController;
import org.controlsfx.control.GridCell;

public class NoteCardCell extends GridCell<NoteCardFxController> {
    @Override
    protected void updateItem(NoteCardFxController item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            setGraphic(item);
        }
    }
}
