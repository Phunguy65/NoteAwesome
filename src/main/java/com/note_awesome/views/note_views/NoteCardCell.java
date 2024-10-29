package com.note_awesome.views.note_views;

import com.note_awesome.models.NoteCardViewModel;
import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.GridCell;

public class NoteCardCell extends GridCell<NoteCardViewModel> {

    public NoteCardFxController getNoteCardFxController() {
        return noteCardFxController;
    }

    private final NoteCardFxController noteCardFxController = new NoteCardFxController();

    public NoteCardCell() {
        super();
        initialize();
    }

    private void initialize() {
        FadeTransition fadeIn = new FadeTransition();

        this.noteCardFxController.getToolBarVbox().visibleProperty().subscribe(val -> {
            if (val) {
                fadeIn.setNode(this.noteCardFxController.getToolBarVbox());
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.setCycleCount(1);
                fadeIn.setAutoReverse(false);
                fadeIn.play();
            }
        });

        this.noteCardFxController.getPinNoteBtn().visibleProperty().subscribe(val -> {
            if (val) {
                fadeIn.setNode(this.noteCardFxController.getPinNoteBtn());
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.setCycleCount(1);
                fadeIn.setAutoReverse(false);
                fadeIn.play();
            }
        });

        this.noteCardFxController.getRootVb().setOnMouseEntered(event -> {
            this.noteCardFxController.getToolBarVbox().setVisible(true);
            this.noteCardFxController.getPinNoteBtn().setVisible(true);
        });

        this.noteCardFxController.getRootVb().setOnMouseExited(event -> {
            this.noteCardFxController.getToolBarVbox().setVisible(false);
            this.noteCardFxController.getPinNoteBtn().setVisible(false);
        });
        this.noteCardFxController.getNoteDescriptionTxtArea().setWrapText(true);
        this.noteCardFxController.getNoteDescriptionTxtArea().setEditable(false);
        this.noteCardFxController.getNoteTitleTxtArea().setWrapText(true);
        this.noteCardFxController.getNoteTitleTxtArea().setEditable(false);
    }

    @Override
    protected void updateItem(NoteCardViewModel item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            setGraphic(null);
        } else {
            this.noteCardFxController.getNoteTitleTxtArea().setText(item.getTitle());
            this.noteCardFxController.getNoteDescriptionTxtArea().setText(item.getContent());
            setGraphic(noteCardFxController);
        }
    }
}
