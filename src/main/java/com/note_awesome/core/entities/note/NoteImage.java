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
    private Long id;

    @Column(name = "image_url", nullable = false, unique = true, length = 255)
    private String imageLocation;

    @Column(name = "is_used", nullable = false, unique = false)
    private boolean using = false;

    @ManyToOne
    @JoinColumn(name = "note_content_id", nullable = false)
    private NoteContent noteContent;

    public NoteImage() {
    }

    public NoteImage(String imageLocation, boolean using, NoteContent noteContent) {
        this.imageLocation = imageLocation;
        this.using = using;
        this.noteContent = noteContent;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String urlLocation) {
        this.imageLocation = urlLocation;
    }

    @Override
    public String toString() {
        return "NoteImage{" +
                "id=" + id +
                ", imageLocation='" + imageLocation + '\'' +
                ", using=" + using +
                ", noteContent=" + noteContent +
                '}';
    }

    public boolean isUsed() {
        return using;
    }

    public void setUsed(boolean using) {
        this.using = using;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NoteContent getNoteContent() {
        return noteContent;
    }
}
