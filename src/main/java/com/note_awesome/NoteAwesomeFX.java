package com.note_awesome;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
public class NoteAwesomeFX {
    public static void main(String[] args) {
        Application.launch(NoteAwesomeApplication.class, args);
    }
}
