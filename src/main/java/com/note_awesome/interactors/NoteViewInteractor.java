package com.note_awesome.interactors;


import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.core.entities.note.NoteImage;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.IInitialize;
import com.note_awesome.extensions.Result;
import com.note_awesome.models.ISessionViewModel;
import com.note_awesome.models.NoteCardViewModel;
import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.services.note_services.INoteContentBaseService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Component
public class NoteViewInteractor implements IInitialize {
    private final INoteContentBaseService noteContentBaseService;
    private final NoteViewModel noteViewModel;
    private final ISessionViewModel sessionViewModel;
    private final NoteEditorViewModel noteEditorViewModel;
    private Optional<NoteContent> noteContent = Optional.empty();
    private final Logger logger = LoggerFactory.getLogger(NoteViewInteractor.class);

    public NoteViewInteractor(INoteContentBaseService noteContentBaseService, NoteViewModel noteViewModel, ISessionViewModel sessionViewModel) {
        this.noteContentBaseService = noteContentBaseService;
        this.noteViewModel = noteViewModel;
        this.sessionViewModel = sessionViewModel;
        this.noteEditorViewModel = this.noteViewModel.getNoteEditorViewModel();
        initialize();
    }

    @Override
    public void initialize() {
        this.noteEditorViewModel.canSaveProperty().bind(Bindings.createBooleanBinding(this::canCreateNote, this.noteEditorViewModel.titleProperty(), this.noteEditorViewModel.getRawContent(), this.noteEditorViewModel.getImages()));
    }

    public void createNote() {
        if (!canCreateNote()) {
            return;
        }
        var newNote = mapNoteEditorViewModelToNoteContent();
        this.noteContentBaseService.getCreateNoteContentService().create(newNote, this.sessionViewModel.getCurrentUsrProfId().get()).Match((noteContent) -> {
            this.noteContent = Optional.of(noteContent);
            return Result.success(noteContent);
        }, error -> {
            logger.error("Error occurred while creating note: {}", error);
            return Result.failure(error);
        });
        refreshNoteEditor();
    }

    private void refreshNoteEditor() {
        this.noteEditorViewModel.titleProperty().set("");
        this.noteEditorViewModel.descriptionProperty().set("");
        this.noteEditorViewModel.getRawContent().clear();
        this.noteEditorViewModel.getImages().clear();
        this.noteEditorViewModel.pinnedProperty().set(false);
    }

    public void updateNoteBoard() {
        this.noteContent.ifPresentOrElse((noteContent) -> {
            if (noteContent.isPinned()) {
                updatePinnedNoteCard(noteContent);
            } else {
                updateUnpinnedNoteCard(noteContent);
            }
        }, () -> logger.error("Note content is not present"));
        flushNoteContent();

    }

    private void updatePinnedNoteCard(NoteContent noteContent) {
        this.noteViewModel.getPinnedNotes().add(new NoteCardViewModel(new SimpleLongProperty(noteContent.getId()), new SimpleStringProperty(noteContent.getTitle()), new SimpleStringProperty(noteContent.getTextContent()), FXCollections.observableArrayList(noteContent.getNoteImages().stream().map(NoteImage::getImageLocation).toList())));
    }

    private void updateUnpinnedNoteCard(NoteContent noteContent) {
        this.noteViewModel.getUnpinnedNotes().add(new NoteCardViewModel(new SimpleLongProperty(noteContent.getId()), new SimpleStringProperty(noteContent.getTitle()), new SimpleStringProperty(noteContent.getTextContent()), FXCollections.observableArrayList(noteContent.getNoteImages().stream().map(NoteImage::getImageLocation).toList())));
    }


    private NoteContent mapNoteEditorViewModelToNoteContent() {
        var noteContent = new NoteContent();
        noteContent.setTitle(this.noteEditorViewModel.getTitle());
        noteContent.setTextContent(this.noteEditorViewModel.getDescription());
        noteContent.setRawContent(ArrayUtils.toPrimitive(this.noteEditorViewModel.getRawContent().toArray(new Byte[0])));
        noteContent.setNoteImages(this.noteEditorViewModel.getImages().stream().map(img -> {
            var noteImage = new NoteImage();
            noteImage.setImageLocation(img);
            noteImage.setUsed(true);
            return noteImage;
        }).toList());
        noteContent.setPinned(this.noteEditorViewModel.isPinned());
        logger.info("Note content created: {}", noteContent);
        return noteContent;
    }

    private boolean canCreateNote() {
        var imgIsExist = !this.noteEditorViewModel.getImages().isEmpty() && this.noteEditorViewModel.getImages().stream().allMatch(img -> Files.exists(Path.of(img)));
        var rawContentIsExist = !this.noteEditorViewModel.getRawContent().isEmpty();
        var titleIsExist = !this.noteEditorViewModel.getTitle().isEmpty();

        var contentIsExist = !this.noteEditorViewModel.getDescription().isEmpty() || titleIsExist || imgIsExist;
        return contentIsExist && rawContentIsExist;
    }

    private void flushNoteContent() {
        this.noteContent = Optional.empty();
    }

    public void pinNoteEditor() {
        this.noteEditorViewModel.pinnedProperty().set(!this.noteEditorViewModel.isPinned());
    }
}
