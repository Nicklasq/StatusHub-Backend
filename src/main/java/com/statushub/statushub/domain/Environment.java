package com.statushub.statushub.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "environments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, length = 32)
    private String name;   // DEV, TEST, QA, PROD

    @NotBlank
    @Column(length = 16)
    private String status; // ACTIVE, FREEZE, DOWN, UPDATING
}
