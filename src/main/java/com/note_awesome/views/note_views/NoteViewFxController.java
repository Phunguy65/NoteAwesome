package com.note_awesome.views.note_views;

import com.note_awesome.models.NoteCardViewModel;
import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.views.core_editors.NoteEditorFxController;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
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

    private final NoteViewModel noteViewModel;

    private final NoteEditorViewModel noteEditorViewModel;

    private final Consumer<Runnable> createNote;

    private final Runnable pinNoteEditor;

    private final Runnable openNoteEditor;

    private final Consumer<Runnable> closeNoteEditor;

    private final NoteEditorFxController updateNoteEditor = new NoteEditorFxController();

    public NoteViewFxController(NoteViewModel noteViewModel, Consumer<Runnable> createNote, Runnable pinNoteEditor, Runnable openNoteEditor, Consumer<Runnable> closeNoteEditor) {
        this.noteViewModel = noteViewModel;
        this.noteEditorViewModel = this.noteViewModel.getNoteEditorViewModel();
        this.createNote = createNote;
        this.pinNoteEditor = pinNoteEditor;
        this.openNoteEditor = openNoteEditor;
        this.closeNoteEditor = closeNoteEditor;
    }

    @FXML
    private void initialize() {

        this.emptyBoardManagerVb.visibleProperty().bind(this.noteViewModel.showAllNotesProperty());
        this.noteBoardManagerVb.visibleProperty().bind(this.noteViewModel.showAllNotesProperty().not());

        this.noteBarFxController.getNoteTriggerTxtField().setOnMouseClicked(event -> openNoteEditor.run());
        this.rootView.setOnMouseClicked(event -> {
            if (this.noteViewModel.showNoteEditorProperty().get()) {
                closeNoteEditor.accept(this::pushNote);
            }
        });

        this.noteEditorFxController.getPinNoteBtn().setOnAction(event -> {
            pinNoteEditor.run();
        });
        this.noteEditorFxController.visibleProperty().bind(this.noteViewModel.showNoteEditorProperty());
        this.noteEditorFxController.managedProperty().bind(this.noteViewModel.showNoteEditorProperty());
        this.noteEditorFxController.visibleProperty().subscribe(this::showNoteEditor);
        this.noteBarFxController.visibleProperty().bind(this.noteViewModel.showNoteEditorProperty().not());
        this.noteEditorFxController.getCloseEditorBtn().setOnAction(event -> closeNoteEditor.accept(this::pushNote));

        this.noteEditorViewModel.pinnedProperty().subscribe(e -> {
            if (e) {
                if (!this.noteEditorFxController.getPinNoteBtn().getStyleClass().contains("pinned")) {
                    this.noteEditorFxController.getPinNoteBtn().getStyleClass().add("pinned");
                }
            } else {
                this.noteEditorFxController.getPinNoteBtn().getStyleClass().remove("pinned");
            }
        });


        this.pinNoteBoardGv.setItems(this.noteViewModel.getPinnedNotes());
        this.unpinNoteBoardGv.setItems(this.noteViewModel.getUnpinnedNotes());
        this.pinNoteBoardGv.setCellFactory(param -> new NoteCardCell(
                this::openUpdateNoteDialog
        ));
        this.unpinNoteBoardGv.setCellFactory(param -> new NoteCardCell(
                this::openUpdateNoteDialog
        ));

        this.noteEditorFxController.getArea().textProperty().subscribe(e -> {
            if (e != null) {
                this.noteEditorViewModel.setDescription(e);
            }
        });

        this.pinNoteBoardManagerVb.visibleProperty().bind(this.noteViewModel.showPinNotesProperty());
        this.pinNoteBoardManagerVb.managedProperty().bind(this.noteViewModel.showPinNotesProperty());

        this.unpinNoteBoardManagerVb.managedProperty().bind(this.noteViewModel.showUnpinnedNotesProperty());
        this.unpinNoteBoardManagerVb.visibleProperty().bind(this.noteViewModel.showUnpinnedNotesProperty());


        this.noteEditorFxController.getNoteTitleTxtArea().textProperty().bindBidirectional(this.noteEditorViewModel.titleProperty());

        this.updateNoteEditor.getCloseEditorBtn().setVisible(false);
        this.updateNoteEditor.getCloseEditorBtn().setManaged(false);

        this.noteEditorFxController.setOnMouseClicked(Event::consume);
    }

    private void refreshNoteEditor() {
        this.noteEditorFxController.getArea().clear();
        this.noteEditorFxController.getNoteTitleTxtArea().setText("");

    }

    private void openUpdateNoteDialog() {
        Dialog<?> dialog = new Dialog<>();
        dialog.getDialogPane().setContent(updateNoteEditor);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        dialog.setOnCloseRequest(event -> {
            closeUpdateNoteDialog(dialog);
        });
        dialog.showAndWait();
    }

    private void closeUpdateNoteDialog(Dialog<?> dialog) {
        updateNoteEditor.getArea().clear();
        updateNoteEditor.getNoteTitleTxtArea().setText("");
        dialog.close();
    }


    private void showNoteEditor(boolean show) {
        if (show) {
            if (!editorManagerSp.getStyleClass().contains("opened")) {
                editorManagerSp.getStyleClass().add("opened");
            }
            noteEditorFxController.requestFocus();
        } else {
            editorManagerSp.getStyleClass().remove("opened");
        }
    }

    private void pushNote() {
        noteEditorViewModel.getRawContent().addAll(noteEditorFxController.getByteContent());
        createNote.accept(this::refreshNoteEditor);
    }
}
