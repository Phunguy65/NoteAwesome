package com.note_awesome;

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
    
    public StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try{
            Stage stage = event.getStage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainWindow.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Scene scene = new Scene(fxmlLoader.load(),1920,1080);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
