package com.note_awesome.core.entities.note;

import com.note_awesome.core.entities.AuditorEntity;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "note_images")
@Audited
public class NoteImage extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "image_url", nullable = false, unique = true, length = 255)
    private String imageLocation;

    @Column(name = "is_used", nullable = false, unique = false)
    private boolean is_used = false;

    @ManyToOne
    @JoinColumn(name = "note_content_id", nullable = false)
    private NoteContent noteContent;

    public NoteImage() {
    }

    public NoteImage(String imageLocation, boolean is_used, NoteContent noteContent) {
        this.imageLocation = imageLocation;
        this.is_used = is_used;
        this.noteContent = noteContent;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String urlLocation) {
        this.imageLocation = urlLocation;
    }

    public boolean isUsed() {
        return is_used;
    }

    public void setUsed(boolean is_used) {
        this.is_used = is_used;
    }

    public long getId() {
        return id;
    }

    public NoteContent getNoteContent() {
        return noteContent;
    }
}
