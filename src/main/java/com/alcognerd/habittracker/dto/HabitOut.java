package com.alcognerd.habittracker.dto;

import com.alcognerd.habittracker.enums.HabitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HabitOut {
    private Long id;
    private String name;
    private String description;
    private String frequency;
    private LocalDate createdAt;
    private HabitStatus habitStatus;
    private LocalDate lastCompletedAt;
    private Integer currentStreak;
    private String category;
}
