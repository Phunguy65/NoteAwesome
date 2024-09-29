package com.note_awesome.views.note_views;

import com.note_awesome.views.core_editors.NoteEditorFxController;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class NoteViewFxController {
    
    @FXML
    private NoteEditorFxController noteEditorFx;
    
    @FXML
    private NoteBarFxController noteBarFxController;
    
    @FXML
    private StackPane spEditorManager;
    
    public NoteViewFxController () {
        
    }
    
    @FXML
    private void initialize(){
        this.spEditorManager.getChildren().get(0).setVisible(false);
        this.spEditorManager.getChildren().get(1).setVisible(true);
    }
}
