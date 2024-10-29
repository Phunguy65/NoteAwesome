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
    private ObservableList<Byte> rawContent;

    public boolean isPinned() {
        return pinned.get();
    }

    public BooleanProperty pinnedProperty() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned.set(pinned);
    }

    private BooleanProperty pinned;

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    private StringProperty description;

    public ObservableList<String> getImages() {
        return images;
    }

    public void setImages(ObservableList<String> images) {
        this.images = images;
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

    public ObservableList<Byte> getRawContent() {
        return rawContent;
    }

    public void setRawContent(ObservableList<Byte> rawContent) {
        this.rawContent = rawContent;
    }

    public void setCanSave(boolean canSave) {
        this.canSave.set(canSave);
    }

    private ObservableList<String> images;

    public boolean isCanSave() {
        return canSave.get();
    }

    public BooleanProperty canSaveProperty() {
        return canSave;
    }

    private BooleanProperty canSave;

    public NoteEditorViewModel() {
        id = new SimpleLongProperty();
        title = new SimpleStringProperty("");
        description = new SimpleStringProperty("");
        rawContent = FXCollections.observableArrayList();
        images = FXCollections.observableArrayList();
        canSave = new SimpleBooleanProperty(false);
        pinned = new SimpleBooleanProperty(false);
    }

    public NoteEditorViewModel(int id, String title, List<Byte> rawContent, List<String> images) {
        this.id = new SimpleLongProperty(id);
        this.title = new SimpleStringProperty(title);
        this.rawContent = FXCollections.observableArrayList(rawContent);
        this.images = FXCollections.observableArrayList(images);
        this.canSave = new SimpleBooleanProperty(false);
    }

    public void refresh() {
        this.id = new SimpleLongProperty();
        this.title = new SimpleStringProperty();
        this.rawContent = FXCollections.observableArrayList();
        this.images = FXCollections.observableArrayList();
        this.canSave = new SimpleBooleanProperty(false);
    }

}
