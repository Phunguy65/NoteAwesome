package com.note_awesome.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class NoteCardViewModel {
    private LongProperty id;

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
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

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public ObservableList<String> getImages() {
        return images;
    }

    public void setImages(ObservableList<String> images) {
        this.images = images;
    }

    private StringProperty title;
    private StringProperty content;
    private ObservableList<String> images;

    public NoteCardViewModel() {
        id = new SimpleLongProperty();
        title = new SimpleStringProperty();
        content = new SimpleStringProperty();
        images = FXCollections.observableArrayList();
    }
}
