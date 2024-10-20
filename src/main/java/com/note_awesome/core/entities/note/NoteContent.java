package com.note_awesome.core.entities.note;

import com.note_awesome.core.entities.AuditorEntity;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "note_contents")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class NoteContent extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text_content", nullable = false, unique = false, length = 1000)
    private String textContent;

    @ElementCollection
    @CollectionTable(name = "note_content_images", joinColumns = @JoinColumn(name = "note_content_id"))
    @Column(name = "image_url", nullable = true, unique = false, length = 100)
    private List<String> imageUrl;

    @Column(name = "title", nullable = true, unique = false, length = 200)
    private String title;

    @Column(name = "is_deleted", nullable = false, unique = false)
    @NotAudited
    private boolean isDeleted;

    public long getId() {
        return id;
    }
    
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_tag_id", nullable = true)
    @NotAudited
    private NoteTag noteTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    @NotAudited
    private UserProfile userProfile;

}