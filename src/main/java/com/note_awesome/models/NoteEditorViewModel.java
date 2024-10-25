package com.note_awesome.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteEditorViewModel {

    private LongProperty id;
    private StringProperty title;

    
    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ObservableList<String> getImages() {
        return images;
    }

    public void setImages(ObservableList<String> images) {
        this.images = images;
    }

    private StringProperty content;
    private ObservableList<String> images;

    public NoteEditorViewModel() {
        id = new SimpleLongProperty();
        title = new SimpleStringProperty();
        content = new SimpleStringProperty();
        images = FXCollections.observableArrayList();
    }

    public NoteEditorViewModel(int id, String title, String content, List<String> images) {
        this.id = new SimpleLongProperty(id);
        this.title = new SimpleStringProperty(title);
        this.content = new SimpleStringProperty(content);
        this.images = FXCollections.observableArrayList(images);
    }

}
