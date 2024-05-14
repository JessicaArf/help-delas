package com.elastech.helpdelas.service;

import com.elastech.helpdelas.model.TicketModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ApiService {

    @Autowired
    private TicketService ticketService;

   // private Integer totalTicketsFinished;
    private BigDecimal timeMediumResponse; // tempo médio de resposta dos chamados (data de abertura até finalização)
    private BigDecimal timeMediumSolution; // tempo médio de solução dos chamados (data de em atendimento até finalização)

    public Integer totalTicketsFinished(){ //numero total de chamados atendido
        List<ApiTicketDTO> countTicket = ticketService.findByStatusTicketStatusLike(TicketModel.TicketStatus.CLOSED);
        return countTicket.size();
    }
}
