package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.PriorityModel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PriorityDTO {
    private Long priorityId;
    private String name;
    private PriorityModel.PrioridadeEnum level ;
    private Instant creationTimestamp;
    private Instant updatedTimestamp;

    public PriorityDTO() {
    }

    public PriorityDTO(PriorityModel priorityModel){
        this.priorityId = priorityModel.getPriorityId();
        this.name = priorityModel.getName();
        this.level = priorityModel.getLevel();
        this.creationTimestamp = priorityModel.getCreationTimestamp();
        this.updatedTimestamp = priorityModel.getUpdatedTimestamp();
    }

    public static PriorityModel convert (PriorityDTO priorityDTO){
        PriorityModel priority = new PriorityModel();
        priority.setPriorityId(priorityDTO.getPriorityId());
        priority.setName(priorityDTO.getName());
        priority.setLevel(priorityDTO.getLevel());
        priority.setCreationTimestamp(priorityDTO.getCreationTimestamp());
        priority.setUpdatedTimestamp(priorityDTO.getUpdatedTimestamp());
        return priority;
    }

}


