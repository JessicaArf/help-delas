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
        Optional<SectorModel> sectorModel = sectorRepository.findByNameSector(dto.getNameSector());
        if(sectorModel.isPresent()){
            throw new RuntimeException("Setor já cadastrado");
        }
        SectorModel sector = SectorDTO.convert(dto);
        sector = sectorRepository.save(sector);
        return new SectorDTO(sector);
    }

    public SectorDTO updateById(SectorDTO dto, Long sectorId){
        Optional<SectorModel> sectorModel = sectorRepository.findById(sectorId);
        System.out.println(sectorModel);
        if(sectorModel.isEmpty()){
            throw new RuntimeException("Setor não encontrado");
        }
        //pegando os dados do BD e fazendo a alteração do nome do setor
        sectorModel.get().setNameSector(dto.getNameSector());
        //pegando os dados do BD e fazendo a alteração do nome do departamento
        sectorModel.get().setNameDepartment(dto.getNameDepartment());
        System.out.println(sectorModel);
        //salvando a informação
        sectorRepository.save(sectorModel.get());
        System.out.println(sectorModel);
        //retornando o novo nome
        return new SectorDTO(sectorModel.get());
    }

    public SectorDTO deleteById(Long sectorId){
        SectorDTO dto = findById(sectorId);
        sectorRepository.deleteById(sectorId);
        return dto;
    }

    public List<SectorDTO> findAllSector(){
        List<SectorModel> sectors = sectorRepository.findAll();
        return sectors.stream().map(SectorDTO::new).collect(Collectors.toList());
    }

    public SectorDTO findById(Long sectorId){
        List<SectorModel> sector = sectorRepository.findAll();
        Optional<SectorModel> resultado = sectorRepository.findById(sectorId);
        if(resultado.isEmpty()) {
            throw new RuntimeException("setor não encontrado");
        } else {
            return new SectorDTO(resultado.get());
        }
    }
}
