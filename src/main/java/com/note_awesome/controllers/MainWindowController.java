package com.note_awesome.controllers;

import com.note_awesome.models.SessionViewModel;
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

    @Autowired
    public MainWindowController(NoteViewController noteViewController, SessionViewModel currentSession) {
        this.mainWindowViewBuilder = new MainWindowViewBuilder(noteViewController.getView());
        this.currentSession = currentSession;

    }


    public Region getView() {
        this.logger.info(currentSession.getCurrentUserId() + " " + currentSession.getCurrentUsrProfId());
        return mainWindowViewBuilder.build();
    }
}
