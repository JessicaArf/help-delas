package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.SectorModel;

public class SectorDTO {

    private Long id;

    private String nameSector;

    public SectorDTO() {

    }

    public SectorDTO(Long id) {
        this.id = id;
    }

    public SectorDTO(SectorModel sector) {
        this.id = sector.getId();
        this.nameSector = sector.getNameSector();
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

    public static SectorModel convert(SectorDTO sectorDTO){
        SectorModel sector = new SectorModel();
        sector.setId(sectorDTO.getId());
        sector.setNameSector(sectorDTO.getNameSector());
        return sector;
    }
}
