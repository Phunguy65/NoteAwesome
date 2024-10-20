package com.note_awesome.views.note_views;

import com.note_awesome.NoteAwesomeEnv;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NoteCardFxController extends VBox {
    public NoteCardFxController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(NoteAwesomeEnv.VIEW_COMPONENT_LOAD_PATHS.get(NoteAwesomeEnv.ViewComponent.NOTE_BAR));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
