package com.note_awesome.views.note_views;

import com.note_awesome.models.NoteCardViewModel;
import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.views.core_editors.NoteEditorFxController;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.apache.commons.lang3.function.Consumers;
import org.controlsfx.control.GridView;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

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

    private final NoteViewModel noteVm;

    private final NoteEditorViewModel createNoteEditorVm;

    private final NoteEditorViewModel updateNoteEditorVm;

    private final Consumer<Runnable> createNote;

    private final Consumer<Runnable> updateNote;

    private final Consumer<Runnable> closeNoteEditor;

    private final Function<Long, Boolean> showUpdateNoteEditor;

    private final BiConsumer<Long, Boolean> switchNoteBoard;

    private final NoteEditorFxController updateNoteEditor = new NoteEditorFxController();

    public NoteViewFxController(NoteViewModel noteVm, Consumer<Runnable> createNote, Consumer<Runnable> updateNote, Consumer<Runnable> closeNoteEditor, Function<Long, Boolean> showUpdateNoteEditor, BiConsumer<Long, Boolean> switchNoteBoard) {
        this.noteVm = noteVm;
        this.createNoteEditorVm = this.noteVm.getCreateNoteEditorVm();
        this.updateNoteEditorVm = this.noteVm.getUpdateNoteEditorVm();
        this.createNote = createNote;
        this.updateNote = updateNote;
        this.closeNoteEditor = closeNoteEditor;
        this.showUpdateNoteEditor = showUpdateNoteEditor;
        this.switchNoteBoard = switchNoteBoard;
    }

    @FXML
    private void initialize() {

        this.emptyBoardManagerVb.visibleProperty().bind(this.noteVm.showAllNotesProperty());
        this.noteBoardManagerVb.visibleProperty().bind(this.noteVm.showAllNotesProperty().not());

        this.noteBarFxController.getNoteTriggerTxtField().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                noteVm.showNoteEditorProperty().set(true);
            }
        });

        this.rootView.setOnMouseClicked(event -> {
            if (this.noteVm.showNoteEditorProperty().get()) {
                closeNoteEditor.accept(this::pushNote);
            }
        });

        this.noteEditorFxController.getPinNoteBtn().setOnAction(e -> this.createNoteEditorVm.pinnedProperty().set(!this.createNoteEditorVm.pinnedProperty().get()));

        this.noteEditorFxController.visibleProperty().bind(this.noteVm.showNoteEditorProperty());
        this.noteEditorFxController.managedProperty().bind(this.noteVm.showNoteEditorProperty());
        this.noteEditorFxController.visibleProperty().subscribe(this::showNoteEditor);
        this.noteBarFxController.visibleProperty().bind(this.noteVm.showNoteEditorProperty().not());
        this.noteEditorFxController.getCloseEditorBtn().setOnAction(event -> closeNoteEditor.accept(this::pushNote));

        this.createNoteEditorVm.pinnedProperty().subscribe(e -> {
            if (e) {
                if (!this.noteEditorFxController.getPinNoteBtn().getStyleClass().contains("pinned")) {
                    this.noteEditorFxController.getPinNoteBtn().getStyleClass().add("pinned");
                }
            } else {
                this.noteEditorFxController.getPinNoteBtn().getStyleClass().remove("pinned");
            }
        });

        this.updateNoteEditor.getPinNoteBtn().setOnAction(e -> this.updateNoteEditorVm.pinnedProperty().set(!this.updateNoteEditorVm.pinnedProperty().get()));
        this.updateNoteEditorVm.pinnedProperty().subscribe(e -> {
            if (e) {
                if (!this.updateNoteEditor.getPinNoteBtn().getStyleClass().contains("pinned")) {
                    this.updateNoteEditor.getPinNoteBtn().getStyleClass().add("pinned");
                }
            } else {
                this.updateNoteEditor.getPinNoteBtn().getStyleClass().remove("pinned");
            }
        });


        this.pinNoteBoardGv.setItems(this.noteVm.getPinnedNotes());
        this.unpinNoteBoardGv.setItems(this.noteVm.getUnpinnedNotes());
        this.pinNoteBoardGv.setCellFactory(param -> new NoteCardCell(
                this::openUpdateNoteDialog, this::switchNoteBoard
        ));
        this.unpinNoteBoardGv.setCellFactory(param -> new NoteCardCell(
                this::openUpdateNoteDialog, this::switchNoteBoard
        ));

        this.noteEditorFxController.getArea().textProperty().subscribe(e -> {
            if (e != null) {
                this.createNoteEditorVm.setDescription(e);
            }
        });

        this.pinNoteBoardManagerVb.visibleProperty().bind(this.noteVm.showPinNotesProperty());
        this.pinNoteBoardManagerVb.managedProperty().bind(this.noteVm.showPinNotesProperty());

        this.unpinNoteBoardManagerVb.managedProperty().bind(this.noteVm.showUnpinnedNotesProperty());
        this.unpinNoteBoardManagerVb.visibleProperty().bind(this.noteVm.showUnpinnedNotesProperty());


        this.noteEditorFxController.getNoteTitleTxtArea().textProperty().bindBidirectional(this.createNoteEditorVm.titleProperty());

        this.updateNoteEditor.getCloseEditorBtn().setVisible(false);
        this.updateNoteEditor.getCloseEditorBtn().setManaged(false);
        this.updateNoteEditor.getNoteTitleTxtArea().textProperty().bindBidirectional(this.updateNoteEditorVm.titleProperty());
        this.updateNoteEditor.getArea().textProperty().subscribe(e -> {
            if (e != null) {
                this.updateNoteEditorVm.setDescription(e);
            }
        });
        this.noteEditorFxController.setOnMouseClicked(Event::consume);
    }

    private void refreshCreateNoteEditor() {
        this.noteEditorFxController.getArea().clear();
        this.noteEditorFxController.getNoteTitleTxtArea().setText("");

    }

    private void openUpdateNoteDialog(Long noteId) {
        if (showUpdateNoteEditor.apply(noteId)) {
            this.updateNoteEditor.load(this.updateNoteEditorVm.getRawContent());
            Dialog<?> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(this.updateNoteEditor);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
            dialog.setOnCloseRequest(event -> {
                this.updateNote.accept(this::refreshUpdateNoteEditor);
            });
            dialog.showAndWait();
        }
    }

    private void refreshUpdateNoteEditor() {
        updateNoteEditor.getArea().clear();
    }

    private void switchNoteBoard(Long noteId, boolean newVal) {
        this.switchNoteBoard.accept(noteId, newVal);
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
        createNoteEditorVm.getRawContent().addAll(noteEditorFxController.getByteContent());
        createNote.accept(this::refreshCreateNoteEditor);
    }
}
