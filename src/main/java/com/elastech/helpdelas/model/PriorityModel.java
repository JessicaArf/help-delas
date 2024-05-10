package com.elastech.helpdelas.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Table(name = "tb_priorities")
@NoArgsConstructor
@AllArgsConstructor
public class PriorityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priorityId;

    @Column(nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadeEnum level;

    public enum PrioridadeEnum {
        BAIXA, MEDIA, ALTA, URGENTE
    }
    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;
}