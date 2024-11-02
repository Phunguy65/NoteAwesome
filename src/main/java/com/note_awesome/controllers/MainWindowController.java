package com.note_awesome.controllers;

import com.note_awesome.models.SessionViewModel;
import com.note_awesome.models.ViewStateViewModel;
import com.note_awesome.views.MainWindowViewBuilder;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MainWindowController {
    private final MainWindowViewBuilder mainWindowViewBuilder;
    private final SessionViewModel currentSession;
    private final Logger logger = LoggerFactory.getLogger(MainWindowController.class);
    private final ViewStateViewModel viewStateViewModel;

    @Autowired
    public MainWindowController(NoteViewController noteViewController, SessionViewModel currentSession, ViewStateViewModel viewStateViewModel) {
        this.mainWindowViewBuilder = new MainWindowViewBuilder(noteViewController.getView());
        this.viewStateViewModel = viewStateViewModel;
        this.currentSession = currentSession;

    }


    public Region getView() {
        this.logger.info(currentSession.getCurrentUserId() + " " + currentSession.getCurrentUsrProfId());
        this.viewStateViewModel.showNoteViewProperty().set(true);
        return mainWindowViewBuilder.build();
    }
}
