package com.note_awesome.views.note_views;

import com.note_awesome.views.core_editors.NoteEditorFxController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.controlsfx.control.GridView;

public class NoteViewFxController {

    @FXML
    private VBox rootView;

    @FXML
    private NoteEditorFxController noteEditorFxController;

    @FXML
    private NoteBarFxController noteBarFxController;

    @FXML
    private StackPane editorManagerSp;

    private final BooleanProperty noteEditorOpened = new SimpleBooleanProperty(false);

    @FXML
    private StackPane boardManagerSp;

    @FXML
    private VBox emptyBoardManagerVb;

    @FXML
    private MaterialIconView emptyMessageIcon;

    @FXML
    private Label emptyMessageLb;

    @FXML
    private VBox noteBoardManagerVb;

    @FXML
    private VBox pinNoteBoardManagerVb;

    @FXML
    private Label pinMessageLb;

    @FXML
    private GridView<NoteCardFxController> pinNoteBoardGv;

    @FXML
    private VBox unpinNoteBoardManagerVb;

    @FXML
    private Label unpinMessageLb;

    @FXML
    private GridView<NoteCardFxController> unpinNoteBoardGv;

    public NoteViewFxController() {

    }

    @FXML
    private void initialize() {

        this.noteEditorFxController.managedProperty().bind(this.noteEditorFxController.visibleProperty());
        this.noteEditorFxController.visibleProperty().subscribe(e -> {
            if (e) {
                if (!this.editorManagerSp.getStyleClass().contains("opened")) {
                    this.editorManagerSp.getStyleClass().add("opened");
                }
                this.noteEditorFxController.requestFocus();
            } else {
                this.editorManagerSp.getStyleClass().remove("opened");
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

        this.editorManagerSp.setOnMouseClicked(e -> {
            e.consume();
        });

    }
}
