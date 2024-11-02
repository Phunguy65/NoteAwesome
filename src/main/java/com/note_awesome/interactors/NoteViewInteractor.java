package com.note_awesome.interactors;


import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.core.entities.note.NoteImage;
import com.note_awesome.extensions.IInitialize;
import com.note_awesome.extensions.Result;
import com.note_awesome.models.ISessionViewModel;
import com.note_awesome.models.NoteCardViewModel;
import com.note_awesome.models.NoteEditorViewModel;
import com.note_awesome.models.NoteViewModel;
import com.note_awesome.services.note_services.INoteContentBaseService;
import javafx.beans.binding.Bindings;
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
    private final NoteViewModel noteVm;
    private final ISessionViewModel sessionViewModel;
    private final NoteEditorViewModel createNoteEditorVm;
    private final NoteEditorViewModel updateNoteEditorVm;
    private Optional<NoteContent> noteContent = Optional.empty();
    private final Logger logger = LoggerFactory.getLogger(NoteViewInteractor.class);

    public NoteViewInteractor(INoteContentBaseService noteContentBaseService, NoteViewModel noteVm, ISessionViewModel sessionViewModel) {
        this.noteContentBaseService = noteContentBaseService;
        this.noteVm = noteVm;
        this.sessionViewModel = sessionViewModel;
        this.createNoteEditorVm = this.noteVm.getCreateNoteEditorVm();
        this.updateNoteEditorVm = this.noteVm.getUpdateNoteEditorVm();
        initialize();
    }

    @Override
    public void initialize() {
        this.createNoteEditorVm.canSaveProperty().bind(Bindings.createBooleanBinding(this::canCreateNote, this.createNoteEditorVm.titleProperty(), this.createNoteEditorVm.getRawContent(), this.createNoteEditorVm.getImages()));

    }

    public void createNote() {
        if (!canCreateNote()) {
            return;
        }
        var newNote = mapNoteEditorVmToNoteContent(this.createNoteEditorVm);
        this.noteContentBaseService.getCreateNoteContentService().create(newNote, this.sessionViewModel.getCurrentUsrProfId().get()).Match((noteContent) -> {
            this.noteContent = Optional.of(noteContent);
            return Result.success(noteContent);
        }, error -> {
            logger.error("Error occurred while creating note: {}", error);
            return Result.failure(error);
        });
        refreshNoteEditor(this.createNoteEditorVm);
    }

    private void refreshNoteEditor(NoteEditorViewModel noteEditorVm) {
        noteEditorVm.setTitle("");
        noteEditorVm.setDescription("");
        noteEditorVm.setRawContent(FXCollections.observableArrayList());
        noteEditorVm.setImages(FXCollections.observableArrayList());
        noteEditorVm.pinnedProperty().set(false);
    }

    public void updateNoteBoard() {
        this.noteContent.ifPresentOrElse((noteContent) -> {
            if (noteContent.pinned()) {
                updatePinnedNoteCard(noteContent);
            } else {
                updateUnpinnedNoteCard(noteContent);
            }
        }, () -> logger.error("Note content is not present"));
        flushNoteContent();
    }

    private void updatePinnedNoteCard(NoteContent noteContent) {
        var noteCardVm = this.mapNoteContentToNoteCardVm(noteContent);
        this.noteVm.getPinnedNotes().add(noteCardVm);
    }

    private void updateUnpinnedNoteCard(NoteContent noteContent) {
        var noteCardVm = this.mapNoteContentToNoteCardVm(noteContent);
        this.noteVm.getUnpinnedNotes().add(noteCardVm);
    }


    private NoteContent mapNoteEditorVmToNoteContent(NoteEditorViewModel noteEditorViewModel) {
        var noteContent = new NoteContent();

        noteContent.setTitle(noteEditorViewModel.getTitle());
        noteContent.setTextContent(noteEditorViewModel.getDescription());
        noteContent.setRawContent(ArrayUtils.toPrimitive(noteEditorViewModel.getRawContent().toArray(new Byte[0])));
        noteContent.setNoteImages(noteEditorViewModel.getImages().stream().map(img -> {
            var noteImage = new NoteImage();
            noteImage.setImageLocation(img);
            noteImage.setUsed(true);
            return noteImage;
        }).toList());

        noteContent.setPinned(noteEditorViewModel.isPinned());
        logger.info("Note content created: {}", noteContent);
        return noteContent;
    }

    private NoteEditorViewModel mapNoteContentToNoteEditorVm(NoteContent noteContent) {
        var noteEditorViewModel = new NoteEditorViewModel();

        noteEditorViewModel.setTitle(noteContent.getTitle());
        noteEditorViewModel.setDescription(noteContent.getTextContent());
        noteEditorViewModel.setRawContent(FXCollections.observableArrayList(ArrayUtils.toObject(noteContent.getRawContent())));
        noteEditorViewModel.setImages(FXCollections.observableArrayList(noteContent.getNoteImages().stream().map(NoteImage::getImageLocation).toList()));
        noteEditorViewModel.pinnedProperty().set(noteContent.pinned());

        logger.info("Note content mapped to note editor view model: {}", noteEditorViewModel);
        return noteEditorViewModel;
    }

    private NoteCardViewModel mapNoteContentToNoteCardVm(NoteContent noteContent) {
        var noteCardViewModel = new NoteCardViewModel();

        noteCardViewModel.setId(noteContent.getId());
        noteCardViewModel.setTitle(noteContent.getTitle());
        noteCardViewModel.setContent(noteContent.getTextContent());
//        noteCardViewModel.setImages(FXCollections.observableArrayList(noteContent.getNoteImages().stream().map(NoteImage::getImageLocation).toList()));
        noteCardViewModel.pinnedProperty().set(noteContent.pinned());

        logger.info("Note content mapped to note card view model: {}", noteCardViewModel);
        return noteCardViewModel;
    }


    private boolean canCreateNote() {
        var imgIsExist = !this.createNoteEditorVm.getImages().isEmpty() && this.createNoteEditorVm.getImages().stream().allMatch(img -> Files.exists(Path.of(img)));
        var rawContentIsExist = !this.createNoteEditorVm.getRawContent().isEmpty();
        var titleIsExist = !this.createNoteEditorVm.getTitle().isEmpty();

        var contentIsExist = !this.createNoteEditorVm.getDescription().isEmpty() || titleIsExist || imgIsExist;
        return contentIsExist && rawContentIsExist;
    }

    private void flushNoteContent() {
        this.noteContent = Optional.empty();
    }


    public void closeNoteEditor() {
        this.noteVm.showNoteEditorProperty().set(false);
    }

    public boolean showUpdateNoteEditor(Long id) {
        if (id == null) {
            this.logger.error("Note id is null");
            return false;
        }
        var getNote = this.noteContentBaseService.getNoteContentQueryService().getNoteContentById(id);

        if (!getNote.isSuccess()) {
            this.logger.error("Error occurred while getting note content: {}", getNote.getError());
            return false;
        }

        var noteVm = mapNoteContentToNoteEditorVm(getNote.getValue());

        this.updateNoteEditorVm.setTitle(noteVm.getTitle());
        this.updateNoteEditorVm.setDescription(noteVm.getDescription());
        this.updateNoteEditorVm.setRawContent(noteVm.getRawContent());
        this.updateNoteEditorVm.setImages(noteVm.getImages());
        this.updateNoteEditorVm.pinnedProperty().set(noteVm.isPinned());
        this.updateNoteEditorVm.setId(id);

        return true;
    }

    public void updateNote() {
        var updatedNote = mapNoteEditorVmToNoteContent(this.updateNoteEditorVm);

        updatedNote.setId(this.updateNoteEditorVm.getId());

        this.noteContentBaseService.getUpdateNoteContentService().update(updatedNote.getId(), updatedNote.getTitle(), updatedNote.getTextContent(), updatedNote.pinned(), updatedNote.getRawContent()).Match((updatedNoteContent) -> {
            this.noteContent = Optional.of(updatedNoteContent);
            return Result.success(updatedNoteContent);
        }, error -> {
            logger.error("Error occurred while updating note: {}", error);
            return Result.failure(error);
        });

        this.noteVm.getUnpinnedNotes().removeIf(noteCardViewModel -> noteCardViewModel.getId() == updatedNote.getId());
        this.noteVm.getPinnedNotes().removeIf(noteCardViewModel -> noteCardViewModel.getId() == updatedNote.getId());
        refreshNoteEditor(this.updateNoteEditorVm);
    }

    public void switchNoteBoard(Long noteId, boolean newVal) {
        if (noteId == null) {
            this.logger.error("Note id is null");
            return;
        }

        var updateNote = this.noteContentBaseService.getUpdateNoteContentService().update(noteId, newVal);

        if (updateNote.isSuccess()) {
            this.noteVm.getPinnedNotes().removeIf(noteCardViewModel -> noteCardViewModel.getId() == noteId);
            this.noteVm.getUnpinnedNotes().removeIf(noteCardViewModel -> noteCardViewModel.getId() == noteId);
            if (updateNote.getValue().pinned()) {
                updatePinnedNoteCard(updateNote.getValue());
            } else {
                updateUnpinnedNoteCard(updateNote.getValue());
            }
        } else {
            this.logger.error("Error occurred while updating note: {}", updateNote.getError());
        }
    }

    public void refreshNoteBoard() {
        this.noteVm.getPinnedNotes().clear();
        this.noteVm.getUnpinnedNotes().clear();

        var noteContents = this.noteContentBaseService.getNoteContentQueryService().getDefaultNoteContents(this.sessionViewModel.getCurrentUsrProfId().get());

        if (noteContents.isSuccess()) {
            noteContents.getValue().forEach(noteContent -> {
                if (noteContent.pinned()) {
                    updatePinnedNoteCard(noteContent);
                } else {
                    updateUnpinnedNoteCard(noteContent);
                }
            });
        } else {
            this.logger.error("Error occurred while getting default note contents: {}", noteContents.getError());
        }
    }


    public void deletePinnedNote(Long noteId) {
        if (noteId == null) {
            this.logger.error("Note id is null");
            return;
        }

        var deleteNote = this.noteContentBaseService.getDeleteNoteContentService().markAsDeleted(noteId);

        if (deleteNote.isSuccess()) {
            this.noteVm.getPinnedNotes().removeIf(noteCardViewModel -> noteCardViewModel.getId() == noteId);
        } else {
            this.logger.error("Error occurred while deleting note: {}", deleteNote.getError());
        }
    }

    public void deleteUnpinnedNote(Long noteId) {
        if (noteId == null) {
            this.logger.error("Note id is null");
            return;
        }

        var deleteNote = this.noteContentBaseService.getDeleteNoteContentService().markAsDeleted(noteId);

        if (deleteNote.isSuccess()) {
            this.noteVm.getUnpinnedNotes().removeIf(noteCardViewModel -> noteCardViewModel.getId() == noteId);
        } else {
            this.logger.error("Error occurred while deleting note: {}", deleteNote.getError());
        }
    }
}
