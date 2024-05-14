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

    public void save(SectorDTO dto) {
        Optional<SectorModel> sectorModel = sectorRepository.findByNameSector(dto.getNameSector());
        if (sectorModel.isPresent()) {
            throw new RuntimeException("Setor já cadastrado.");
        }
        SectorModel sector = SectorDTO.convert(dto);
        sectorRepository.save(sector);
    }

    public void updateById(SectorDTO dto, Long sectorId) {
        Optional<SectorModel> sectorModel = sectorRepository.findById(sectorId);
        if (sectorModel.isEmpty()) {
            throw new RuntimeException("Setor não encontrado.");
        }
        //pegando os dados do BD e fazendo a alteração do nome do setor
        sectorModel.get().setNameSector(dto.getNameSector());
        //pegando os dados do BD e fazendo a alteração do nome do departamento
        sectorModel.get().setNameDepartment(dto.getNameDepartment());
        //salvando a informação
        sectorRepository.save(sectorModel.get());
    }

    public List<SectorDTO> findAllSector() {
        List<SectorModel> sectors = sectorRepository.findAll();
        return sectors.stream().map(SectorDTO::new).collect(Collectors.toList());
    }

    public SectorDTO findById(Long sectorId) {
        Optional<SectorModel> result = sectorRepository.findById(sectorId);
        if (result.isEmpty()) {
            throw new RuntimeException("Setor não encontrado.");
        } else {
            return new SectorDTO(result.get());
        }
    }

    public void deleteById(Long sectorId) {
        Optional<SectorModel> sectorModel = sectorRepository.findById(sectorId);
        if (sectorModel.isEmpty()) {
            throw new RuntimeException("Setor não encontrado");
        }
        sectorRepository.delete(sectorModel.get());
    }
}
