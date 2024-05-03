package com.elastech.helpdelas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // anotação para que seja uma única tabela
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING) // anotação para criar uma coluna discriminadora chamada user_type para poder guardar o tipo de cliente criado
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role")
    private RoleModel role;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;

}
