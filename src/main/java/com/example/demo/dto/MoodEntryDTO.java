package com.example.demo.dto;

import com.example.demo.entity.MoodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MoodEntryDTO {

    @NotNull(message = "Mood type is required")   // ✅ mood button must be selected
    private MoodType moodType;

    @NotBlank(message = "Feeling cannot be empty")   // ✅ user typed short feeling
    @Size(max = 100, message = "Feeling must be under 100 characters")
    private String feeling;

    @Size(max = 500, message = "Note cannot exceed 500 characters")   // ✅ longer detail note
    private String note;

    // ===== Getters and Setters =====

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
}
