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

    public TicketDTO createTicket(TicketDTO ticketDTO, UserDTO basicUser){
        UserModel basicUserModel = UserDTO.convert(basicUser);
        ticketDTO.setBasicUser(basicUserModel);
        ticketDTO.setStatus(TicketModel.TicketStatus.OPEN);
        TicketModel ticketModel = TicketDTO.convert(ticketDTO);
        ticketRepository.save(ticketModel);
        return ticketDTO;
    }

   public List<TicketDTO> showTicketsByUser(Long userId){
        List<TicketModel> tickets = ticketRepository.findByUserBasicUserId(userId);
       return tickets.stream()
               .map(TicketDTO::new)
               .collect(Collectors.toList());
   }

}
