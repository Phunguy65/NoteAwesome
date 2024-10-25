package com.note_awesome.views.note_views;

import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.views.core_editors.NoteEditorFxController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
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

    private EditorManagerSpHandler editorManagerSpHandler;

    private final NoteViewModel noteViewModel;

    public NoteViewFxController(NoteViewModel noteViewModel) {
        this.noteViewModel = noteViewModel;
    }

    @FXML
    private void initialize() {
        this.editorManagerSpHandler = new EditorManagerSpHandler();
        this.noteBarFxController.getNoteTriggerTxtField().setOnMouseClicked(event -> editorManagerSpHandler.OpenEditor());
        this.rootView.setOnMouseClicked(event -> editorManagerSpHandler.CloseEditor());
//        this.editorManagerSp.setOnMouseClicked(Event::consume);
        this.noteEditorFxController.setOnMouseClicked(Event::consume);
    }

    private class EditorManagerSpHandler {

        private final BooleanProperty noteEditorOpened = new SimpleBooleanProperty(false);

        {
            noteEditorFxController.visibleProperty().bind(noteEditorOpened);
            noteBarFxController.visibleProperty().bind(noteEditorOpened.not());
            noteEditorFxController.managedProperty().bind(noteEditorFxController.visibleProperty());

            noteEditorFxController.visibleProperty().subscribe(e -> {
                if (e) {
                    if (!editorManagerSp.getStyleClass().contains("opened")) {
                        editorManagerSp.getStyleClass().add("opened");
                    }
                    noteEditorFxController.requestFocus();
                } else {
                    editorManagerSp.getStyleClass().remove("opened");
                }
            });
        }

        public void OpenEditor() {
            noteEditorOpened.set(true);
        }

        public void CloseEditor() {
            noteEditorOpened.set(false);
        }
    }
}
