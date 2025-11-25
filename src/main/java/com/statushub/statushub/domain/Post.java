    package com.statushub.statushub.domain;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import lombok.*;

    import java.time.Instant;

    @Entity
    @Table(name = "posts")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Column(length = 120)
        private String title;

        @NotBlank
        @Column(length = 2000)
        private String description;

        @NotBlank
        @Column(length = 16)
        private String type; // DEPLOY, PATCH, FREEZE, INFO

        private Instant createdAt;

        @ManyToOne(optional = false)
        @JoinColumn(name = "environment_id")
        private Environment environment;

        @PrePersist
        public void onCreate() {
            if (createdAt == null) {
                createdAt = Instant.now();
            }
        }
    }
