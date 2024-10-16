package com.note_awesome.views;

import com.note_awesome.NoteAwesomeFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.springframework.beans.factory.annotation.Autowired;

public class MainWindowViewBuilder implements Builder<Region> {

    private final Region noteView;
    
    public MainWindowViewBuilder(Region noteView) {
        this.noteView = noteView;
    }

    @Override
    public Region build() {
        var loader = new FXMLLoader(NoteAwesomeFX.class.getResource("fxml/MainWindow.fxml"));
        loader.setController(new MainWindowFxController(noteView));
        try {
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
