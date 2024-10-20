package com.note_awesome.views.note_views;

import com.note_awesome.NoteAwesomeEnv;
import com.note_awesome.NoteAwesomeFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;


public class NoteBarFxController extends HBox {
    @FXML
    private TextField noteTriggerTxtField;

    @FXML
    private Button addImagesBtn;

    public NoteBarFxController() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(NoteAwesomeEnv.VIEW_COMPONENT_LOAD_PATHS.get(NoteAwesomeEnv.ViewComponent.NOTE_BAR));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        this.noteTriggerTxtField.setPromptText("Note here...");
    }

    public TextField getNoteTriggerTxtField() {
        return noteTriggerTxtField;
    }

    public Button getAddImagesBtn() {
        return addImagesBtn;
    }
}
