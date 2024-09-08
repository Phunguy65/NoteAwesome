package com.note_awesome;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@ComponentScans({
        @ComponentScan(basePackages = "com.note_awesome.models.entities.note"),
        @ComponentScan(basePackages = "com.note_awesome.models.repositories.note"),
        @ComponentScan(basePackages = "com.note_awesome.views")
})
public class NoteAwesomeApplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }
    
    @Override
    public void init() throws Exception {
        this.applicationContext = new SpringApplicationBuilder().web(WebApplicationType.NONE)
                .sources(NoteAwesomeFX.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }
    
    @Override
    public void stop() throws Exception {
        this.applicationContext.close();
        Platform.exit();
    }
    
}