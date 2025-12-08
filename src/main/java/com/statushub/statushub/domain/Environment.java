package com.statushub.statushub.domain;

import jakarta.persistence.*;
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

    // F.eks. "PROD", "TEST", "DEV"
    @Column(nullable = false)
    private String name;

    // F.eks. "OK", "ISSUES", "FREEZE", "DOWN"
    @Column(nullable = false)
    private String status;

    // Hvilken løsning/system miljøet hører til
    // F.eks. "LEGO", "FÆTTER BR", "MATAS"
    @Column(name = "solution_name")
    private String solutionName;
}
