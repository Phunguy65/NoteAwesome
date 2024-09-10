package com.note_awesome.models.entities.note;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "note_content")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class NoteContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long contentId;
    
    @Column(name = "text_content", nullable = false, unique = false, length = 1000)
    private String textContent;
    
    @Column(name = "image_url", nullable = true, unique = false, length = 100)
    private String imageUrl;
    
    @Column(name = "title", nullable = true, unique = false, length = 50)
    private String title;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public NoteTag getNoteTag() {
        return noteTag;
    }

    public void setNoteTag(NoteTag noteTag) {
        this.noteTag = noteTag;
    }

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notetag_id", nullable = false)
    @NotAudited
    private NoteTag noteTag;
    
}
