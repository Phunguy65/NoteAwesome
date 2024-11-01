package com.note_awesome.core.repositories.note;

import com.note_awesome.core.entities.note.NoteImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INoteImageRepository extends JpaRepository<NoteImage, Long> {
    List<NoteImage> findByNoteContent_IdAndUsing(@NonNull long id, @NonNull boolean using);

}