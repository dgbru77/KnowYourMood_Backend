package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "mood_entries")
public class MoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)    // ✅ store mood button selection
    private MoodType moodType;

    @NotBlank(message = "Feeling cannot be blank") // ✅ new short input text
    private String feeling;

    private String note; // ✅ detailed explanation

    private LocalDateTime dateTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // ===== Constructors =====
    public MoodEntry() {}

    public MoodEntry(MoodType moodType, String feeling, String note, User user) {
        this.moodType = moodType;
        this.feeling = feeling;
        this.note = note;
        this.user = user;
    }

    // ===== Getters and Setters =====

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public MoodType getMoodType() {
        return moodType;
    }
    public void setMoodType(MoodType moodType) {
        this.moodType = moodType;
    }

    public String getFeeling() {
        return feeling;
    }
    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
