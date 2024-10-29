package com.note_awesome.views.note_views;

import com.note_awesome.models.NoteCardViewModel;
import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.views.core_editors.NoteEditorFxController;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import java.util.function.Consumer;

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
    private GridView<NoteCardViewModel> pinNoteBoardGv;

    @FXML
    private VBox unpinNoteBoardManagerVb;

    @FXML
    private Label unpinMessageLb;

    @FXML
    private GridView<NoteCardViewModel> unpinNoteBoardGv;

    private EditorManagerSpHandler editorManagerSpHandler;

    private final NoteViewModel noteViewModel;

    private final NoteEditorViewModel noteEditorViewModel;

    private final Consumer<Runnable> createNote;

    private final Runnable pinNoteEditor;

    public NoteViewFxController(NoteViewModel noteViewModel, Consumer<Runnable> createNote, Runnable pinNoteEditor) {
        this.noteViewModel = noteViewModel;
        this.noteEditorViewModel = this.noteViewModel.getNoteEditorViewModel();
        this.createNote = createNote;
        this.pinNoteEditor = pinNoteEditor;
    }

    @FXML
    private void initialize() {

        this.editorManagerSpHandler = new EditorManagerSpHandler();

        this.emptyBoardManagerVb.visibleProperty().bind(this.noteViewModel.showAllNotesProperty());
        this.noteBoardManagerVb.visibleProperty().bind(this.noteViewModel.showAllNotesProperty().not());
        this.noteBarFxController.getNoteTriggerTxtField().setOnMouseClicked(event -> editorManagerSpHandler.OpenEditor());
        this.rootView.setOnMouseClicked(event -> editorManagerSpHandler.CloseEditor());

        this.noteEditorFxController.getPinNoteBtn().setOnAction(event -> {
            pinNoteEditor.run();
        });

        this.noteEditorViewModel.pinnedProperty().subscribe(e -> {
            if (e) {
                if (!this.noteEditorFxController.getPinNoteBtn().getStyleClass().contains("pinned")) {
                    this.noteEditorFxController.getPinNoteBtn().getStyleClass().add("pinned");
                }
            } else {
                this.noteEditorFxController.getPinNoteBtn().getStyleClass().remove("pinned");
            }
        });

        this.noteEditorFxController.getCloseEditorBtn().setOnAction(event -> {
            editorManagerSpHandler.CloseEditor();
        });

        this.pinNoteBoardGv.setItems(this.noteViewModel.getPinnedNotes());
        this.unpinNoteBoardGv.setItems(this.noteViewModel.getUnpinnedNotes());
        this.pinNoteBoardGv.setCellFactory(param -> new NoteCardCell());
        this.unpinNoteBoardGv.setCellFactory(param -> new NoteCardCell());

        this.noteEditorFxController.getArea().textProperty().subscribe(e -> {
            if (e != null) {
                this.noteEditorViewModel.setDescription(e);
            }
        });

        this.noteEditorFxController.getNoteTitleTxtArea().textProperty().bindBidirectional(this.noteEditorViewModel.titleProperty());

        this.noteEditorFxController.setOnMouseClicked(Event::consume);
    }

    private void refreshNoteEditor() {
        this.noteEditorFxController.getArea().clear();
        this.noteEditorFxController.getNoteTitleTxtArea().setText("");

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
            noteEditorViewModel.getRawContent().addAll(noteEditorFxController.getByteContent());
            createNote.accept(NoteViewFxController.this::refreshNoteEditor);
        }

        public BooleanProperty noteEditorOpenedProperty() {
            return noteEditorOpened;
        }
    }
}
