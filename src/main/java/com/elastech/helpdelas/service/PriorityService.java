package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.PriorityDTO;
import com.elastech.helpdelas.model.PriorityModel;
import com.elastech.helpdelas.repositories.PriorityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriorityService {

    @Autowired
    private PriorityRepository priorityRepository;

    public void save(PriorityDTO priorityDTO) {
        Optional<PriorityModel> priorityModel = priorityRepository.findByNamePriority(priorityDTO.getNamePriority());
        if (priorityModel.isPresent()) {
            throw new RuntimeException("Prioridade já cadastrada");
        }
        PriorityModel priority = PriorityDTO.convert(priorityDTO);
        priorityRepository.save(priority);
    }

    public List<PriorityDTO> findAllPriority() {
        List<PriorityModel> priorities = priorityRepository.findAll();
        return priorities.stream().map(PriorityDTO::new).collect(Collectors.toList());
    }

    public PriorityDTO findById(Long priorityId) {
        Optional<PriorityModel> resultado = priorityRepository.findById(priorityId);
        if (resultado.isEmpty()) {
            throw new RuntimeException("Setor não encontrado");
        } else {
            return new PriorityDTO(resultado.get());
        }
    }

    public PriorityDTO updateById(PriorityDTO priorityDTO, Long priorityId) {
        Optional<PriorityModel> priorityExists = priorityRepository.findById(priorityId);
        if (priorityExists.isEmpty()) {
            throw new RuntimeException("Prioridade não encontrada");
        }
        PriorityModel priorityModel = priorityExists.get();
        priorityModel.setNamePriority(priorityDTO.getNamePriority());
        priorityModel.setDescription(priorityDTO.getDescription());
        priorityRepository.save(priorityModel);
        return new PriorityDTO(priorityModel);
    }

    public void deleteById(Long priorityId) {
        Optional<PriorityModel> priority = priorityRepository.findById(priorityId);
        if (priority.isEmpty()) {
            throw new EntityNotFoundException("Prioridade não encontrada");
        }
        priorityRepository.delete(priority.get());
    }

}
