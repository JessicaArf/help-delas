package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.TicketDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.TicketModel;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketDTO createTicket(TicketDTO ticketDTO, UserDTO userBasic){
        UserModel userBasicModel = UserDTO.convert(userBasic);
        ticketDTO.setUserBasic(userBasicModel);
        ticketDTO.setStatus(TicketModel.TicketStatus.OPEN);
        TicketModel ticketModel = TicketDTO.convert(ticketDTO);
        ticketRepository.save(ticketModel);
        return ticketDTO;
    }

   public List<TicketDTO> showTicketsByUser(Long userBasicId){
        List<TicketModel> tickets = ticketRepository.findByUserBasicUserId(userBasicId);
       return tickets.stream()
               .map(TicketDTO::new)
               .collect(Collectors.toList());
   }

   public List<TicketDTO> showTicketsAvailable(){
       List<TicketModel> tickets = ticketRepository.findByUserTechUserIdIsNull();
       return tickets.stream()
               .map(TicketDTO::new)
               .collect(Collectors.toList());
   }

    public List<TicketDTO> showTicketsAssigned(Long userTechId){
        List<TicketModel> tickets = ticketRepository.findByUserTechUserId(userTechId);
        return tickets.stream()
                .map(TicketDTO::new)
                .collect(Collectors.toList());
    }



}
