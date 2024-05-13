package com.elastech.helpdelas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB_PASSWORD_TOKEN")
public class RequestPasswordModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    private LocalDateTime expireTime;

    private boolean isUsed;

}
