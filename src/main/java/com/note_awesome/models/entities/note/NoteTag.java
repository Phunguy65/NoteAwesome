package com.note_awesome.models.entities.note;

import com.note_awesome.models.entities.AuditorEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "note_tags")
public class NoteTag extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "tag_name", nullable = false, unique = true, length = 50)
    private String tagName;

    public long getId() {
        return id;
    }

    public void setId(long tagId) {
        this.id = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<NoteContent> getNoteTagMaps() {
        return noteContents;
    }

    public void setNoteTagMaps(Set<NoteContent> noteContents) {
        this.noteContents = noteContents;
    }

    @OneToMany(mappedBy = "noteTag", cascade = CascadeType.ALL)
    private Set<NoteContent> noteContents;
}
