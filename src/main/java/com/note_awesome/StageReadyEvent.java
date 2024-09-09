package com.note_awesome;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(Stage stage) {
        super(stage);
    }
    
    public Stage getStage() {
        return (Stage) this.getSource();
    }
}
