package com.note_awesome.views;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class MainWindowFxController {

    @FXML
    private StackPane viewManager;

    private final Region noteView;

    public MainWindowFxController(Region noteView) {
        this.noteView = noteView;
    }

    @FXML
    public void initialize() {
        noteView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        viewManager.getChildren().add(noteView);
    }


}
