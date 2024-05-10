package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.PriorityModel;
import com.elastech.helpdelas.model.SectorModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriorityDTO {

    private Long priorityId;
    private String namePriority;
    private String description;

    public PriorityDTO() {

    }

    public PriorityDTO(PriorityModel priority) {
        this.priorityId = priority.getPriorityId();
        this.namePriority = priority.getNamePriority();
        this.description = priority.getDescription();
    }

    public static PriorityModel convert(PriorityDTO priorityDTO){
        PriorityModel priorityModel = new PriorityModel();
        priorityModel.setPriorityId(priorityDTO.getPriorityId());
        priorityModel.setNamePriority(priorityDTO.getNamePriority());
        priorityModel.setDescription(priorityDTO.getDescription());
        return priorityModel;
    }
}
