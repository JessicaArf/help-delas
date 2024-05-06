package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.SectorModel;
import com.elastech.helpdelas.model.TicketModel;
import com.elastech.helpdelas.model.UserModel;
import lombok.Getter;
import lombok.Setter;


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

    public TicketDTO(TicketModel ticketModel){
        this.ticketId = ticketModel.getTicketId();
        this.description = ticketModel.getDescription();
        this.status = ticketModel.getStatus();
        this.basicUser = ticketModel.getBasicUser();
        this.techUser = ticketModel.getTechUser();
    }

    public static TicketModel convert(TicketDTO ticketDTO){
        TicketModel ticket = new TicketModel();
        ticket.setTicketId(ticketDTO.getTicketId());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setSector(ticketDTO.getSector());
        ticket.setBasicUser(ticketDTO.getBasicUser());
        ticket.setTechUser(ticketDTO.getTechUser());
        return ticket;
    }
}
