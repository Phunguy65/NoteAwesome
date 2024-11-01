package com.note_awesome.core.repositories.note;

import com.note_awesome.core.entities.note.NoteContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface INoteContentRepository extends JpaRepository<NoteContent, Long> {
    @Transactional
    @Modifying
    @Query("update NoteContent n set n.title = ?1, n.textContent = ?2, n.rawContent = ?3, n.pinned = ?4 where n.id = ?5")
    int update(@NonNull String title, @NonNull String textContent, @NonNull byte[] rawContent, @NonNull boolean pinned, @NonNull Long id);
    

}