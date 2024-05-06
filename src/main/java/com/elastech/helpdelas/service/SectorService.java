package com.elastech.helpdelas.service;

import com.elastech.helpdelas.model.SectorModel;
import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    public SectorDTO save(SectorDTO dto){
        SectorModel sector = SectorDTO.convert(dto);
        sector = this.sectorRepository.save(sector);
        return new SectorDTO(sector);

    }
    public SectorDTO updateById(SectorDTO dto, Long id){
        this.findById(id);
        SectorModel sector = SectorDTO.convert(dto);
        sector.setId(id);
        sector = this.sectorRepository.save(sector);
        return new SectorDTO(sector);
    }
    public SectorDTO deleteById(Long id){
        SectorDTO dto = this.findById(id);
        this.sectorRepository.deleteById(id);
        return dto;
    }

    public List<SectorDTO> findAll(){
        List<SectorModel> sectors = this.sectorRepository.findAll();
        return sectors.stream().map(SectorDTO::new).collect(Collectors.toList());
    }

    public SectorDTO findById(Long id){
        List<SectorModel> sector = this.sectorRepository.findAll();
        Optional<SectorModel> resultado = this.sectorRepository.findById(id);
        if (resultado.isEmpty()) {
            throw new RuntimeException("setor não encontrado");
        } else {
            return new SectorDTO(resultado.get());
        }
    }

}
