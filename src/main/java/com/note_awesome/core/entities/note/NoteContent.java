package com.note_awesome.core.entities.note;

import com.note_awesome.core.entities.AuditorEntity;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "note_contents")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class NoteContent extends AuditorEntity {
    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text_content", nullable = false, unique = false, length = 1000)
    private String textContent;

    @Column(name = "title", nullable = true, unique = false, length = 200)
    private String title;

    @Column(name = "is_deleted", nullable = false, unique = false)
    @NotAudited
    private boolean deleted = false;

    public boolean pinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    @Column(name = "is_pinned", nullable = false, unique = false)
    @NotAudited
    private boolean pinned = false;

    @Column(name = "note_location", nullable = false, unique = true)
    private String noteLocation = "";

    public NoteContent(String textContent, String title, boolean deleted, boolean pinned, String noteLocation, byte[] rawContent, NoteTag noteTag, UserProfile userProfile, List<NoteImage> noteImages) {
        this.textContent = textContent;
        this.title = title;
        this.deleted = deleted;
        this.pinned = pinned;
        this.noteLocation = noteLocation;
        this.rawContent = rawContent;
        this.noteTag = noteTag;
        this.userProfile = userProfile;
        this.noteImages = noteImages;
    }

    public byte[] getRawContent() {
        return rawContent;
    }

    public void setRawContent(byte[] rawContent) {
        this.rawContent = rawContent;
    }

    @Column(name = "raw_content", nullable = false, unique = false, length = 100000)
    @Lob
    private byte[] rawContent;

    public String getNoteLocation() {
        return noteLocation;
    }

    public void setNoteLocation(String urlLocation) {
        this.noteLocation = urlLocation;
    }

    public NoteContent() {
    }

    public NoteContent(String title, String textContent, String noteLocation, List<NoteImage> images, NoteTag noteTag, UserProfile userProfile) {
        this.textContent = textContent;
        this.noteImages = images;
        this.title = title;
        this.noteLocation = noteLocation;
        this.noteTag = noteTag;
        this.userProfile = userProfile;
    }

    public long getId() {
        return id;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public List<NoteImage> getNoteImages() {
        return noteImages;
    }

    public void setNoteImages(List<NoteImage> noteImages) {
        this.noteImages = noteImages;
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
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_tag_id", nullable = true)
    @NotAudited
    private NoteTag noteTag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id", nullable = false)
    @NotAudited
    private UserProfile userProfile;

    @OneToMany(mappedBy = "noteContent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NoteImage> noteImages;

    @Override
    public String toString() {
        return "NoteContent{" +
                "id=" + id +
                ", textContent='" + textContent + '\'' +
                ", title='" + title + '\'' +
                ", isDeleted=" + deleted +
                ", isPinned=" + pinned +
                ", noteLocation='" + noteLocation + '\'' +
                ", rawContent=" + Arrays.toString(rawContent) +
                ", noteTag=" + noteTag +
                ", userProfile=" + userProfile +
                ", noteImages=" + noteImages +
                '}';
    }
}
