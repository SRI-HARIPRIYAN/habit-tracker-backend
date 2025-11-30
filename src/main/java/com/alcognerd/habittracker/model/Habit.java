    package com.alcognerd.habittracker.model;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.DayOfWeek;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.Set;

    @Entity
    @Table(name = "habits")
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class Habit {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "habit_id")
        private Long habitId;

        @Column(nullable = false)
        private String name;

        @ManyToOne
        @JoinColumn(name = "user_id",referencedColumnName = "id")
        private User user;

        private String description;

        private String frequency;

        @ElementCollection(targetClass = DayOfWeek.class)
        @CollectionTable(
                name = "habit_active_days",
                joinColumns = @JoinColumn(name = "habit_id")
        )
        @Column(name = "day_of_week", nullable = false)
        @Enumerated(EnumType.STRING)
        private Set<DayOfWeek> activeDays;

        @ManyToOne
        @JoinColumn(name = "category_id",referencedColumnName = "category_id")
        private Category category;

        private Boolean enabled;

        public Habit(String name, User user, String description, String frequency, Category category) {
            this.name = name;
            this.user = user;
            this.description = description;
            this.frequency = frequency;
            this.category = category;
        }

        //know if habit is active on a given date
        public boolean isActiveOn(LocalDate date) {
            return activeDays != null && activeDays.contains(date.getDayOfWeek());
        }

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        @PrePersist
        protected void onCreate() {
            LocalDateTime now = LocalDateTime.now();
            this.createdAt = now;
            this.updatedAt = now;
            if (enabled == null) {
                enabled = true;
            }
        }

        @PreUpdate
        protected void onUpdate() {
            this.updatedAt = LocalDateTime.now();
        }

    }
