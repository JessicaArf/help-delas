package com.elastech.helpdelas.service;

import com.elastech.helpdelas.model.PriorityModel;
import com.elastech.helpdelas.repositories.PriorityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class PriorityService {
    @Autowired
    private PriorityRepository prioridadeRepository;

    // criar e salvar uma nova prioridade
    @PostMapping
    public ResponseEntity<PriorityModel> save(@RequestBody PriorityModel prioridade) {
        prioridade = this.prioridadeRepository.save(prioridade);
        return new ResponseEntity<>(prioridade, HttpStatus.CREATED);
    }

    // visualizar todas as prioridades
    @GetMapping
    public ResponseEntity<List<PriorityModel>> findAll() {
        List<PriorityModel> prioridades = this.prioridadeRepository.findAll();
        return new ResponseEntity<>(prioridades, HttpStatus.OK);
    }

    // visualizar uma prioridade específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<PriorityModel> findById(@PathVariable Long id) {
        return this.prioridadeRepository.findById(id)
                .map(prioridade -> new ResponseEntity<>(prioridade, HttpStatus.OK))
                .orElseThrow(() -> new EntityNotFoundException("Prioridade não encontrada com o ID: " + id));
    }

    // atualizar uma prioridade existente
    @PutMapping("/{id}")
    public ResponseEntity<PriorityModel> update(@PathVariable Long id, @RequestBody PriorityModel prioridadeAtualizada) {
        return this.prioridadeRepository.findById(id)
                .map(prioridade -> {
                    prioridade.setName(prioridadeAtualizada.getName());
                    prioridade.setLevel(prioridadeAtualizada.getLevel());
                    prioridade.setPriorityId(prioridadeAtualizada.getPriorityId());
                    prioridade.setCreationTimestamp(prioridadeAtualizada.getCreationTimestamp());
                    prioridade.setUpdatedTimestamp(prioridadeAtualizada.getUpdatedTimestamp());
                    // Atualize os outros campos conforme necessário
                    prioridade = this.prioridadeRepository.save(prioridade);
                    return new ResponseEntity<>(prioridade, HttpStatus.OK);
                })
                .orElseThrow(() -> new EntityNotFoundException("Prioridade não encontrada com o ID: " + id));
    }

    // deletar uma prioridade por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (this.prioridadeRepository.existsById(id)) {
            this.prioridadeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prioridade não encontrada com o ID: " + id);
        }
    }
}



}
