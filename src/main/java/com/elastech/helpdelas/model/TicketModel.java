package com.elastech.helpdelas.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_TICKETS")
public class TicketModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketId;
    private String subject;
    private String description;
    private TicketStatus status;
    private String annotation;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sector")
    private SectorModel sector;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_priority")
    private PriorityModel priority;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_basic_user")
    private UserModel userBasic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tech_user")
    private UserModel userTech;

    public enum TicketStatus {
        OPEN("Aguardando técnico"),
        IN_PROGRESS("Em atendimento"),
        FORWARDED("Escalado para outro setor"),
        CLOSED("Finalizado");

        private final String description;

        TicketStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}
