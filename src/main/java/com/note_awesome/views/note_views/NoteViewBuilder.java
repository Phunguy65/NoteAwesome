package com.note_awesome.views.note_views;

import com.note_awesome.NoteAwesomeFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.util.Builder;


public class NoteViewBuilder implements Builder<Region> {
    public NoteViewBuilder() {
    }

    @Override
    public Region build() {
        var loader = new FXMLLoader(NoteAwesomeFX.class.getResource("fxml/note_views/NoteView.fxml"));
        loader.setController(new NoteViewFxController());
        try {
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
