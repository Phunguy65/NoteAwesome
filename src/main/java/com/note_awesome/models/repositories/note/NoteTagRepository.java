package com.note_awesome.models.repositories.note;

import com.note_awesome.models.entities.note.NoteTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteTagRepository extends JpaRepository<NoteTag, Long> {
    
}
