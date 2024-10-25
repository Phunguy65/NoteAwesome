package com.note_awesome.services.note_services.validators;

import com.note_awesome.core.entities.note.NoteContent;
import com.note_awesome.core.repositories.note.INoteImageRepository;
import com.note_awesome.core.repositories.note.INoteTagRepository;
import com.note_awesome.core.repositories.note.IUserProfileRepository;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import com.note_awesome.services.note_services.NoteContentAbstractValidator;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class CreateNoteContentValidator extends NoteContentAbstractValidator {
    private final IUserProfileRepository userProfileRepository;

    public static class CreateNoteContentError {
        public static Error USER_PROFILE_NOT_FOUND = new Error("User profile not found", "User profile not found");

        // public static Error TAGS_NOT_VALID = new Error("Tags not valid", "Tags not valid");
        public static Error IMAGES_NOT_FOUND(String imageUrl) {
            return new Error("Image not found", "Image not found: " + imageUrl);
        }

    }

    public CreateNoteContentValidator(NoteContentBasicValidator noteContentBasicValidator, IUserProfileRepository userProfileRepository, INoteTagRepository noteTagRepository, INoteImageRepository noteImageRepository) {
        super(noteContentBasicValidator);
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Result<NoteContent, Error> validate(NoteContent noteContent) {
        var resultBasic = super.validate(noteContent);
        if (!resultBasic.isSuccess()) {
            return resultBasic;
        }
        if (!validateUserProfile(noteContent)) {
            return Result.failure(CreateNoteContentError.USER_PROFILE_NOT_FOUND);
        }
        if (!validateImages(noteContent)) {
            return Result.failure(CreateNoteContentError.IMAGES_NOT_FOUND(noteContent.getNoteImages().get(0).getUrlLocation()));
        }
        return Result.success(noteContent);
    }

    // this method is not implemented in the original code
    private boolean validateTags(NoteContent noteContent) {
        return true;
    }

    // this method is not implemented in the original code
    private boolean validateImages(NoteContent noteContent) {
        if (noteContent.getNoteImages() != null) {
            for (var noteImage : noteContent.getNoteImages()) {
                if (!Files.exists(Paths.get(noteImage.getUrlLocation()))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateUserProfile(NoteContent noteContent) {
        if (noteContent.getUserProfile() == null) {
            return false;
        }
        return userProfileRepository.existsById(noteContent.getUserProfile().getId());
    }
}
