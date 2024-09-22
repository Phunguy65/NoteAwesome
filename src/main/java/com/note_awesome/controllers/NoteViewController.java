package com.note_awesome.controllers;

import com.note_awesome.views.note_views.NoteViewBuilder;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class NoteViewController {
    private final NoteViewBuilder noteViewBuilder;
    
    public NoteViewController(){
        this.noteViewBuilder = new NoteViewBuilder();
    }
    
    public Region getView() {
        return noteViewBuilder.build();
    }
}
