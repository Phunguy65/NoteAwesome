package com.note_awesome.core.entities.note;

import com.note_awesome.core.entities.AuditorEntity;
import jakarta.persistence.*;

import java.util.List;
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
    
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<NoteContent> getNoteTagMaps() {
        return noteContents;
    }

    public void setNoteTagMaps(List<NoteContent> noteContents) {
        this.noteContents = noteContents;
    }

    @OneToMany(mappedBy = "noteTag", cascade = CascadeType.ALL)
    private List<NoteContent> noteContents;
}
