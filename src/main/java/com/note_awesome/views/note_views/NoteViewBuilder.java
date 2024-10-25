package com.note_awesome.views.note_views;

import com.note_awesome.NoteAwesomeFX;
import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.springframework.stereotype.Component;

public class NoteViewBuilder implements Builder<Region> {
    private final NoteViewModel noteViewModel;

    public NoteViewBuilder(NoteViewModel noteViewModel) {
        this.noteViewModel = noteViewModel;
    }

    @Override
    public Region build() {
        var loader = new FXMLLoader(NoteAwesomeFX.class.getResource("fxml/note_views/NoteView.fxml"));
        loader.setController(new NoteViewFxController(noteViewModel));
        try {
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
