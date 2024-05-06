package com.elastech.helpdelas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
<<<<<<< HEAD
=======
@Table(name = "TB_SECTORS")
>>>>>>> 28ed294f42ffcf9b1ede90b7d8608fa096a57c49
public class SectorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nameSector;

    public SectorModel() {

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
