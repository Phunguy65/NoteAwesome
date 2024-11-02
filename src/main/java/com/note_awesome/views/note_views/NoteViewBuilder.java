package com.note_awesome.views.note_views;

import com.note_awesome.NoteAwesomeFX;
import com.note_awesome.models.NoteViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.util.Builder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class NoteViewBuilder implements Builder<Region> {
    private final NoteViewModel noteViewModel;
    private final Consumer<Runnable> createNote;
    private final Consumer<Runnable> closeNoteEditor;
    private final Function<Long, Boolean> showUpdateNoteEditor;
    private final Consumer<Runnable> updateNote;
    private final BiConsumer<Long, Boolean> switchNoteBoard;
    private final BiConsumer<Long, Boolean> deleteNote;

    public NoteViewBuilder(NoteViewModel noteViewModel, Consumer<Runnable> createNote, Consumer<Runnable> updateNote, Consumer<Runnable> closeNoteEditor, Function<Long, Boolean> showUpdateNoteEditor, BiConsumer<Long, Boolean> switchNoteBoard, BiConsumer<Long, Boolean> deleteNote) {
        this.noteViewModel = noteViewModel;
        this.createNote = createNote;
        this.closeNoteEditor = closeNoteEditor;
        this.showUpdateNoteEditor = showUpdateNoteEditor;
        this.updateNote = updateNote;
        this.switchNoteBoard = switchNoteBoard;
        this.deleteNote = deleteNote;
    }

    @Override
    public Region build() {
        var loader = new FXMLLoader(NoteAwesomeFX.class.getResource("fxml/note_views/NoteView.fxml"));
        loader.setController(new NoteViewFxController(noteViewModel, createNote, updateNote, closeNoteEditor, showUpdateNoteEditor, switchNoteBoard, deleteNote));
        try {
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
