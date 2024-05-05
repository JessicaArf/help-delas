package com.elastech.helpdelas.model;

import jakarta.persistence.*;

@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nameSector;

    public Sector() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSector() {
        return nameSector;
    }

    public void setNameSector(String nameSector) {
        this.nameSector = nameSector;
    }
}
