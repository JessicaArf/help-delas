package com.elastech.helpdelas.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_TICKETS")
public class ticketsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketsId;

}
