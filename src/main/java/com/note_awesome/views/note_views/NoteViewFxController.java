package com.note_awesome.views.note_views;

import com.note_awesome.views.core_editors.NoteEditorFxController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NoteViewFxController {

    @FXML
    private VBox rootView;

    @FXML
    private NoteEditorFxController noteEditorFxController;

    @FXML
    private NoteBarFxController noteBarFxController;

    @FXML
    private StackPane spEditorManager;

    private BooleanProperty noteEditorOpened = new SimpleBooleanProperty(false);

    public NoteViewFxController() {

    }

    @FXML
    private void initialize() {

        this.noteEditorFxController.visibleProperty().subscribe(e -> {
            if (e) {
                if (!this.spEditorManager.getStyleClass().contains("opened")) {
                    this.spEditorManager.getStyleClass().add("opened");
                }
            } else {
                this.spEditorManager.getStyleClass().remove("opened");
            }
        });

        this.noteBarFxController.visibleProperty().bind(noteEditorOpened.not());
        this.noteEditorFxController.visibleProperty().bind(noteEditorOpened);

        this.noteBarFxController.getNoteTriggerTxtField().setOnMouseClicked(e -> {
            if (!noteEditorOpened.get()) {
                noteEditorOpened.set(true);
            }
        });

        this.rootView.setOnMouseClicked(e -> {
            if (noteEditorOpened.get()) {
                noteEditorOpened.set(false);
            }
        });

        this.spEditorManager.setOnMouseClicked(e -> {
            e.consume();
        });

    }
}
