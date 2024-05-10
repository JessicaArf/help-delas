package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.SectorModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SectorDTO {

    private Long sectorId;
    private String nameSector;
    private String nameDepartment;

    public SectorDTO() {

    }

    public SectorDTO(SectorModel sector) {
        this.sectorId = sector.getSectorId();
        this.nameSector = sector.getNameSector();
        this.nameDepartment = sector.getNameDepartment();
    }

    public static SectorModel convert(SectorDTO sectorDTO){
        SectorModel sector = new SectorModel();
        sector.setSectorId(sectorDTO.getSectorId());
        sector.setNameSector(sectorDTO.getNameSector());
        sector.setNameDepartment(sectorDTO.getNameDepartment());
        return sector;
    }
}
