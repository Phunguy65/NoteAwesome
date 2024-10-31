package com.note_awesome.views.note_views;

import com.note_awesome.NoteAwesomeEnv;
import com.note_awesome.models.NoteCardViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NoteCardFxController extends VBox {

    @FXML
    private TextArea noteTitleTxtArea;

    public TextArea getNoteDescriptionTxtArea() {
        return noteDescriptionTxtArea;
    }

    public TextArea getNoteTitleTxtArea() {
        return noteTitleTxtArea;
    }

    public VBox getRootVb() {
        return rootVb;
    }

    public VBox getToolBarVbox() {
        return toolBarVbox;
    }

    public Button getPinNoteBtn() {
        return pinNoteBtn;
    }

    @FXML
    private TextArea noteDescriptionTxtArea;

    @FXML
    private VBox rootVb;

    @FXML
    private VBox toolBarVbox;

    @FXML
    private Button pinNoteBtn;


    public NoteCardFxController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(NoteAwesomeEnv.ViewComponent.NOTE_CARD.getURL());
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public NoteCardFxController(NoteCardViewModel noteCardViewModel) {
        this();
        noteTitleTxtArea.setText(noteCardViewModel.getTitle());
        noteDescriptionTxtArea.setText(noteCardViewModel.getContent());
    }

    @FXML
    private void initialize() {
        
    }
}
