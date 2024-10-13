package com.note_awesome.models.repositories.note;

import com.note_awesome.models.entities.note.NoteContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteContentRepository extends JpaRepository<NoteContent, Long> {
}