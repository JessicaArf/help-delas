package com.elastech.helpdelas.dtos;

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
    private String description;
    private TicketModel.TicketStatus status;
    private SectorModel sector;
    /*
    private PriorityModel priority;
     */
    private UserModel basicUser;
    private UserModel techUser;
    private Instant creationTimestamp;
    private Instant updatedTimestamp;

    public TicketDTO(){

    }

    public TicketDTO(TicketModel ticketModel){
        this.ticketId = ticketModel.getTicketId();
        this.description = ticketModel.getDescription();
        this.status = ticketModel.getStatus();
        this.sector = ticketModel.getSector();
        this.basicUser = ticketModel.getUserBasic();
        this.techUser = ticketModel.getUserTech();
        this.creationTimestamp = ticketModel.getCreationTimestamp();
        this.updatedTimestamp = ticketModel.getUpdatedTimestamp();
    }

    public static TicketModel convert(TicketDTO ticketDTO){
        TicketModel ticket = new TicketModel();
        ticket.setTicketId(ticketDTO.getTicketId());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setSector(ticketDTO.getSector());
        ticket.setUserBasic(ticketDTO.getBasicUser());
        ticket.setUserTech(ticketDTO.getTechUser());
        ticket.setCreationTimestamp(ticketDTO.getCreationTimestamp());
        ticket.setUpdatedTimestamp(ticketDTO.getUpdatedTimestamp());
        return ticket;
    }

}
