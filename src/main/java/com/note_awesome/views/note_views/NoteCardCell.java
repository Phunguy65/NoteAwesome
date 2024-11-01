package com.note_awesome.views.note_views;

import com.note_awesome.models.NoteCardViewModel;
import javafx.animation.FadeTransition;
import javafx.beans.property.LongProperty;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.GridCell;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class NoteCardCell extends GridCell<NoteCardViewModel> {

    /**
     * work around for CAN MEMORY LEAK
     */

    private Long noteId;

    private Boolean pinned;

    private Consumer<Long> openNoteEditor;

    private BiConsumer<Long, Boolean> switchNoteBoard;

    private final NoteCardFxController noteCardFxController = new NoteCardFxController();

    public NoteCardCell(Consumer<Long> openNoteEditor, BiConsumer<Long, Boolean> switchNoteBoard) {
        super();

        this.openNoteEditor = openNoteEditor;
        this.switchNoteBoard = switchNoteBoard;
        initialize();
    }

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

        this.noteCardFxController.getNoteTitleTxtArea().setMouseTransparent(true);
        this.noteCardFxController.getNoteDescriptionTxtArea().setMouseTransparent(true);

        this.setOnMouseEntered(event -> {
            this.noteCardFxController.getToolBarVbox().setVisible(true);
            this.noteCardFxController.getPinNoteBtn().setVisible(true);
            event.consume();
        });

        this.setOnMouseExited(event -> {
            this.noteCardFxController.getToolBarVbox().setVisible(false);
            this.noteCardFxController.getPinNoteBtn().setVisible(false);
            event.consume();
        });

        this.noteCardFxController.setOnMouseClicked(event -> {
            openNoteEditor.accept(noteId);
        });


        this.noteCardFxController.getNoteDescriptionTxtArea().setWrapText(true);
        this.noteCardFxController.getNoteDescriptionTxtArea().setEditable(false);
        this.noteCardFxController.getNoteTitleTxtArea().setWrapText(true);
        this.noteCardFxController.getNoteTitleTxtArea().setEditable(false);
    }

    private void flush() {
        this.noteCardFxController.getNoteTitleTxtArea().setText("");
        this.noteCardFxController.getNoteDescriptionTxtArea().setText("");
        this.noteCardFxController.setOnMouseClicked(Event::consume);
        this.noteId = null;
        this.pinned = null;
    }

    @Override
    protected void updateItem(NoteCardViewModel item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(null);
            flush();
        } else {

            this.noteCardFxController.getNoteTitleTxtArea().setText(item.getTitle());
            this.noteCardFxController.getNoteDescriptionTxtArea().setText(item.getContent());
            this.noteCardFxController.setOnMouseClicked(event -> {
                openNoteEditor.accept(item.getId());
            });

            this.pinned = item.isPinned();
            this.noteId = item.getId();

            if (item.isPinned()) {
                if (!this.noteCardFxController.getPinNoteBtn().getStyleClass().contains("pinned")) {
                    this.noteCardFxController.getPinNoteBtn().getStyleClass().add("pinned");
                }
            } else {
                this.noteCardFxController.getPinNoteBtn().getStyleClass().remove("pinned");
            }
            setGraphic(noteCardFxController);
        }
    }
}
