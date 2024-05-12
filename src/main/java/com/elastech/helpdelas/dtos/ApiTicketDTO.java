package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.PriorityModel;
import com.elastech.helpdelas.model.TicketModel;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiTicketDTO {

    private String subject;
    private String annotation;
    private PriorityModel priority;

    public ApiTicketDTO(ApiTicketDTO apiTicketDTO) {
        this.subject = apiTicketDTO.getSubject();
        this.annotation = apiTicketDTO.getAnnotation();
        this.priority = apiTicketDTO.getPriority();
    }

    public ApiTicketDTO(TicketModel ticketModel) {
        this.subject = ticketModel.getSubject();
        this.annotation = ticketModel.getAnnotation();
        this.priority = ticketModel.getPriority();
    }
}
