package com.note_awesome.services.note_services;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.core.entities.note.NoteImage;
import com.note_awesome.core.repositories.note.INoteContentRepository;
import com.note_awesome.core.repositories.note.INoteTagRepository;
import com.note_awesome.core.repositories.note.IUserProfileRepository;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;
import com.note_awesome.services.note_services.validators.CreateNoteContentValidator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class CreateNoteContentService implements ICreateNoteContentService {
    private final INoteContentRepository noteContentRepository;
    private final NoteContentAbstractValidator createNoteContentValidator;
    private final IUserProfileRepository userProfileRepository;
    private final INoteTagRepository tagRepository;

    @Autowired
    public CreateNoteContentService(INoteContentRepository noteContentRepository, IUserProfileRepository userProfileRepository, INoteTagRepository tagRepository, CreateNoteContentValidator createNoteContentValidator) {
        this.noteContentRepository = noteContentRepository;
        this.createNoteContentValidator = createNoteContentValidator;
        this.userProfileRepository = userProfileRepository;
        this.tagRepository = tagRepository;
    }

    private String generateUniqueName() {
        var maxLength = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder uniqueProfileName = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < maxLength; i++) {
            uniqueProfileName.append(chars.charAt(random.nextInt(chars.length())));
        }

        return uniqueProfileName.toString();
    }

    private Result<ArrayList<NoteImage>, Error> copyImagesToNoteLocation(List<NoteImage> imageUrls, Path noteLocation) {
        var copiedImageUrls = new ArrayList<NoteImage>();
        for (var imageUrl : imageUrls) {
            try {
                var srcFile = new File(imageUrl.getImageLocation());
                var ext = FilenameUtils.getExtension(imageUrl.getImageLocation());
                var destFile = new File(FilenameUtils.concat(noteLocation.toString(), generateUniqueName() + "." + ext));
                int count = 0;

                while (destFile.exists() && count < 2) {
                    destFile = new File(FilenameUtils.concat(noteLocation.toString(), generateUniqueName() + "." + ext));
                    count++;
                }

                if (destFile.exists()) {
                    return Result.failure(new Error("Error copying image to note location", "Error copying image to note location"));
                }

                FileCopyUtils.copy(srcFile, destFile);
                copiedImageUrls.add(new NoteImage(destFile.getAbsolutePath(), imageUrl.isUsed(), imageUrl.getNoteContent()));

            } catch (IOException e) {
                for (var copiedImageUrl : copiedImageUrls) {
                    try {
                        Files.deleteIfExists(Path.of(copiedImageUrl.getImageLocation()));
                    } catch (IOException ex) {
                        return Result.failure(new Error("Error copying image to note location", "Error copying image to note location"));
                    }
                }
                return Result.failure(new Error("Error copying image to note location", "Error copying image to note location"));
            }
        }
        return Result.success(copiedImageUrls);
    }

    private Result<Path, Error> createNoteLocation(NoteContent noteContent) {

        var userProfileLocation = noteContent.getUserProfile().getProfileLocation();

        Path noteLocation;
        int count = 0;

        do {
            noteLocation = Path.of(userProfileLocation, generateUniqueName());
            count++;
        } while (Files.exists(noteLocation) && count < 10);

        if (Files.exists(noteLocation)) {
            return Result.failure(new Error("Error creating note location", "Error creating note location"));
        }

        try {
            Files.createDirectories(noteLocation);
        } catch (Exception e) {
            return Result.failure(new Error("Error creating note location", "Error creating note location"));
        }

        return Result.success(noteLocation);
    }


    private Result<NoteContent, Error> save(NoteContent noteContent) {
        try {
            this.noteContentRepository.save(noteContent);
            return Result.success(noteContent);
        } catch (Exception e) {
            return Result.failure(new Error("Error saving note content", "Error saving note content"));
        }
    }

    private Result<NoteContent, Error> procedure(NoteContent noteContent) {
        var validationResult = this.createNoteContentValidator.validate(noteContent);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }
        var noteLocationResult = createNoteLocation(noteContent);
        if (!noteLocationResult.isSuccess()) {
            return Result.failure(noteLocationResult.getError());
        }
        var copiedImagesResult = copyImagesToNoteLocation(noteContent.getNoteImages(), noteLocationResult.getValue());
        if (!copiedImagesResult.isSuccess()) {
            return Result.failure(copiedImagesResult.getError());
        }

        var newNoteContent = new NoteContent();
        newNoteContent.setTextContent(noteContent.getTextContent());
        newNoteContent.setTitle(noteContent.getTitle());
        newNoteContent.setNoteImages(copiedImagesResult.getValue());
        newNoteContent.setNoteLocation(noteLocationResult.getValue().toString());
        newNoteContent.setNoteTag(noteContent.getNoteTag());
        newNoteContent.setUserProfile(noteContent.getUserProfile());
        newNoteContent.setRawContent(noteContent.getRawContent());
        newNoteContent.setPinned(noteContent.pinned());
        return Result.success(newNoteContent);
    }


    @Override
    @Transactional
    public Result<NoteContent, Error> create(NoteContent noteContent) {
        return procedure(noteContent).Match(this::save, Result::failure);
    }

    @Override
    @Transactional
    public Result<NoteContent, Error> create(NoteContent noteContent, long userProfileId) {
        var userProfile = this.userProfileRepository.findById(userProfileId).orElse(null);
        noteContent.setUserProfile(userProfile);
        return create(noteContent);
    }

    @Override
    @Transactional
    public Result<NoteContent, Error> create(NoteContent noteContent, long userProfileId, long tagId) {
        var userProfile = this.userProfileRepository.getReferenceById(userProfileId);
        var tag = this.tagRepository.getReferenceById(tagId);
        noteContent.setUserProfile(userProfile);
        noteContent.setNoteTag(tag);
        return create(noteContent);
    }
}
