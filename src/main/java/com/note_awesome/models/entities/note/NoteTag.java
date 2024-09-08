package com.note_awesome.models.entities.note;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "note_tags")
public class NoteTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tagId;
    
    @Column(name = "tag_name", nullable = false, unique = true, length = 50)
    private String tagName;

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
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
