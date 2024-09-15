package com.note_awesome;

import com.note_awesome.controllers.MainWindowController;
import com.note_awesome.views.MainWindowView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final ApplicationContext applicationContext;
    private final MainWindowController mainWindowController;
    
    public StageInitializer(ApplicationContext applicationContext, MainWindowController mainWindowController) {
        this.applicationContext = applicationContext;
        this.mainWindowController = mainWindowController;
    }
    
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try{
            Stage stage = event.getStage();
            Scene scene = new Scene(mainWindowController.getView(), 1920, 1080);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
