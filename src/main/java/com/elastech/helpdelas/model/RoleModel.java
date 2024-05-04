package com.elastech.helpdelas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_ROLES")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String name;

    public enum Values {
        ADMIN(1L),
        TECH(2L),
        USER(3L);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }

}
