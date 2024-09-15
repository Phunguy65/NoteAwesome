package com.note_awesome.controllers;

import com.note_awesome.views.MainWindowViewBuilder;
import javafx.scene.layout.Region;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class MainWindowController {
    private final MainWindowViewBuilder mainWindowViewBuilder;

    public MainWindowController(){
        this.mainWindowViewBuilder = new MainWindowViewBuilder();
    }

    public Region getView(){
        return mainWindowViewBuilder.build();
    }
}
