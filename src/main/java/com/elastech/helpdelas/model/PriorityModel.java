package com.elastech.helpdelas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PRIORITIES")
public class PriorityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priorityId;

    @Column(unique = true)
    private String namePriority;

    @Column(length = 1000)
    private String description;
}


