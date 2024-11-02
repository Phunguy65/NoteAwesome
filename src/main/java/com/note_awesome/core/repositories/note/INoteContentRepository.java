package com.note_awesome.core.repositories.note;

import com.note_awesome.core.entities.note.NoteContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface INoteContentRepository extends JpaRepository<NoteContent, Long> {
    @Transactional
    @Modifying
    @Query("update NoteContent n set n.title = ?1, n.textContent = ?2, n.rawContent = ?3, n.pinned = ?4 where n.id = ?5")
    int update(@NonNull String title, @NonNull String textContent, @NonNull byte[] rawContent, @NonNull boolean pinned, @NonNull Long id);

    @Query("select n from NoteContent n LEFT JOIN FETCH n.noteImages i where n.userProfile.id = ?1 and n.deleted = false")
    List<NoteContent> findDefaultNoteContentByUsrProfId(@NonNull Long userProfileId);

    @Transactional
    @Modifying
    @Query("update NoteContent n set n.deleted = ?1 where n.id = ?2")
    int updateDeletedById(@NonNull boolean deleted, @NonNull Long id);


}