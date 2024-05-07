package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.TicketDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.TicketModel;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserService userService;

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


    public TicketDTO showTicketById(Long id){
        Optional<TicketModel> ticketModel = ticketRepository.findById(id);
        if (ticketModel.isEmpty()){
            throw new EntityNotFoundException("Chamado não encontrado");
        }
        return new TicketDTO(ticketModel.get());
    }

    public TicketDTO updateTicketUser(Long id, TicketDTO updateTicket){
        Optional<TicketModel> ticketModel = ticketRepository.findById(id);

        if (ticketModel.isEmpty()){
            throw new EntityNotFoundException("Chamado não encontrado");
        }
        ticketModel.get().setDescription(updateTicket.getDescription());
        ticketModel.get().setSubject(updateTicket.getSubject());
        ticketRepository.save(ticketModel.get());

        return new TicketDTO(ticketModel.get());

    }

    public TicketDTO updateTicketTech(Long id, TicketDTO updateTicket, UserDTO userTech){
        Optional<TicketModel> ticketModel = ticketRepository.findById(id);

        if (ticketModel.isEmpty()){
            throw new EntityNotFoundException("Chamado não encontrado");
        }

        UserModel userTechModel = UserDTO.convert(userTech);

        ticketModel.get().setStatus(updateTicket.getStatus());
        ticketModel.get().setSector(updateTicket.getSector());
        ticketModel.get().setUserTech(userTechModel);
        ticketRepository.save(ticketModel.get());

        return new TicketDTO(ticketModel.get());

    }

}
