package com.note_awesome.models.entities.note;

import com.note_awesome.models.entities.AuditorEntity;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "note_content")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class NoteContent extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "text_content", nullable = false, unique = false, length = 1000)
    private String textContent;
    
    @Column(name = "image_url", nullable = true, unique = false, length = 100)
    private String imageUrl;
    
    @Column(name = "title", nullable = true, unique = false, length = 50)
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long contentId) {
        this.id = contentId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NoteTag getNoteTag() {
        return noteTag;
    }

    public void setNoteTag(NoteTag noteTag) {
        this.noteTag = noteTag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notetag_id", nullable = false)
    @NotAudited
    private NoteTag noteTag;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    @NotAudited
    private UserProfile userProfile;
    
}
