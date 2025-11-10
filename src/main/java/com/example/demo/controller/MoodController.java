package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.MoodEntryDTO;
import com.example.demo.entity.MoodEntry;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.MoodEntryService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mood")
public class MoodController {

    @Autowired
    private MoodEntryService moodEntryService;

    @Autowired
    private JwtUtil jwtUtil;

    // 📝 Add mood entry
    @PostMapping("/add")
    public ResponseEntity<?> addMoodEntry(@RequestBody MoodEntryDTO moodEntryDTO, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid token");
        }

        String token = authHeader.substring(7);
        Long userId = Long.parseLong(jwtUtil.extractUsername(token)); // userId stored in JWT

        String response = moodEntryService.addMoodEntry(moodEntryDTO, userId);
        return ResponseEntity.ok(response);
    }

    // 📊 Get mood history
    @GetMapping("/history")
    public ResponseEntity<?> getMoodHistory(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid token");
        }
        String token = authHeader.substring(7);
        Long userId = Long.parseLong(jwtUtil.extractUsername(token));

        List<MoodEntry> history = moodEntryService.getMoodHistory(userId);
        return ResponseEntity.ok(history);
    }
    @PostMapping("/ai/insight")
    public ResponseEntity<?> getInsight(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("insight", "Based on your mood history, here is an insight..."));
    }

}