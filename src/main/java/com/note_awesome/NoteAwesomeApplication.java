package com.note_awesome;

import com.note_awesome.services.IAppBaseService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@ComponentScans({
        @ComponentScan(basePackages = "com.note_awesome.core.repositories.note"),
        @ComponentScan(basePackages = "com.note_awesome.views"),
        @ComponentScan(basePackages = "com.note_awesome.services")
})
@EntityScan(basePackages = {"com.note_awesome.core.entities", "com.note_awesome.core.entities.note"})
public class NoteAwesomeApplication extends Application {

    private ConfigurableApplicationContext applicationContext;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.applicationContext.getBean(IAppBaseService.class).initialize();
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
