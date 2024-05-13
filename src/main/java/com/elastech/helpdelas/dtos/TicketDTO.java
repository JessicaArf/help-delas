package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.PriorityModel;
import com.elastech.helpdelas.model.SectorModel;
import com.elastech.helpdelas.model.TicketModel;
import com.elastech.helpdelas.model.UserModel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
public class TicketDTO {

    private long ticketId;
    private String subject;
    private String description;
    private TicketModel.TicketStatus status;
    private String annotation;
    private SectorModel sector;
    private PriorityModel priority;
    private UserModel userBasic;
    private UserModel userTech;
    private Instant creationTimestamp;
    private Instant updatedTimestamp;

    public TicketDTO(){

    }

    public TicketDTO(TicketModel ticketModel){
        this.ticketId = ticketModel.getTicketId();
        this.subject = ticketModel.getSubject();
        this.description = ticketModel.getDescription();
        this.status = ticketModel.getStatus();
        this.annotation = ticketModel.getAnnotation();
        this.sector = ticketModel.getSector();
        this.priority = ticketModel.getPriority();
        this.userBasic = ticketModel.getUserBasic();
        this.userTech = ticketModel.getUserTech();
        this.creationTimestamp = ticketModel.getCreationTimestamp();
        this.updatedTimestamp = ticketModel.getUpdatedTimestamp();
    }

    public static TicketModel convert(TicketDTO ticketDTO){
        TicketModel ticket = new TicketModel();
        ticket.setTicketId(ticketDTO.getTicketId());
        ticket.setSubject(ticketDTO.getSubject());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setAnnotation(ticketDTO.getAnnotation());
        ticket.setSector(ticketDTO.getSector());
        ticket.setPriority(ticketDTO.getPriority());
        ticket.setUserBasic(ticketDTO.getUserBasic());
        ticket.setUserTech(ticketDTO.getUserTech());
        ticket.setCreationTimestamp(ticketDTO.getCreationTimestamp());
        ticket.setUpdatedTimestamp(ticketDTO.getUpdatedTimestamp());
        return ticket;
    }

}
