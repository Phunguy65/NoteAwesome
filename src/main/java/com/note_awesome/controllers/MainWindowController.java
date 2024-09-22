package com.note_awesome.controllers;

import com.note_awesome.views.MainWindowViewBuilder;
import javafx.scene.layout.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MainWindowController {
    private final MainWindowViewBuilder mainWindowViewBuilder;
    
    private final NoteViewController noteViewController;
    
    @Autowired
    public MainWindowController(NoteViewController noteViewController){
        this.noteViewController = noteViewController;
        this.mainWindowViewBuilder = new MainWindowViewBuilder(noteViewController.getView());
    }
    

    public Region getView(){
        return mainWindowViewBuilder.build();
    }
}
