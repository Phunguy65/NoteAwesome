package com.note_awesome;

import com.note_awesome.controllers.MainWindowController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.antlr.v4.runtime.misc.NotNull;
import org.scenicview.ScenicView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final MainWindowController mainWindowController;

    public StageInitializer(ApplicationContext applicationContext, MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            Stage stage = event.getStage();
            Scene scene = new Scene(mainWindowController.getView());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            ScenicView.show(scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
