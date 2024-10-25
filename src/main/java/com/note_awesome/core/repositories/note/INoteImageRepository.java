package com.note_awesome.core.repositories.note;

import com.note_awesome.core.entities.note.NoteImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INoteImageRepository extends JpaRepository<NoteImage, Long> {
}