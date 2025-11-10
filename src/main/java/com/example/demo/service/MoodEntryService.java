package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MoodEntryDTO;
import com.example.demo.entity.MoodEntry;
import com.example.demo.entity.User;
import com.example.demo.repository.MoodEntryRepository;
import com.example.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MoodEntryService {

    @Autowired
    private MoodEntryRepository moodEntryRepository;

    @Autowired
    private UserRepository userRepository;

    // 📝 Add Mood Entry
    public String addMoodEntry(MoodEntryDTO dto, Long userId) {

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return "User not found!";
        }

        User user = userOpt.get();

        MoodEntry moodEntry = new MoodEntry();
        moodEntry.setUser(user);
        moodEntry.setMoodType(dto.getMoodType());  // ✅ ENUM mood (button click)
        moodEntry.setFeeling(dto.getFeeling());     // ✅ short field input
        moodEntry.setNote(dto.getNote());           // ✅ detailed input
        moodEntry.setDateTime(LocalDateTime.now());

        moodEntryRepository.save(moodEntry);
        return "Mood entry added successfully!";
    }

    // 📊 Fetch Mood History
    public List<MoodEntry> getMoodHistory(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(moodEntryRepository::findByUser).orElse(null);
    }
}
