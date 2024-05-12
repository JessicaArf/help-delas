package com.elastech.helpdelas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_PASSWORD_TOKEN")
public class RequestPasswordModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    private LocalDateTime expireTime;

    private boolean isUsed;

}
