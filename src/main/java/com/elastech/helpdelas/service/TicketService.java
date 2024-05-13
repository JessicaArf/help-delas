package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.TicketDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.TicketModel;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.TicketRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    public TicketDTO createTicket(TicketDTO ticketDTO, UserDTO userBasic) throws MessagingException, UnsupportedEncodingException {
        UserModel userBasicModel = UserDTO.convert(userBasic);
        ticketDTO.setUserBasic(userBasicModel);
        ticketDTO.setSector(userBasicModel.getSector());
        ticketDTO.setStatus(TicketModel.TicketStatus.OPEN);
        TicketModel ticketModel = TicketDTO.convert(ticketDTO);
        ticketRepository.save(ticketModel);
        sendNewTicketEmail(ticketModel);
        sendNewTicketEmailTech(ticketModel);
        return ticketDTO;
    }

    public List<TicketDTO> showTicketsByUser(Long userBasicId) {
        List<TicketModel> tickets = ticketRepository.findByUserBasicUserId(userBasicId);
        return checkIfListEmpty(tickets);
    }

    public List<TicketDTO> showTicketsAvailable() {
        List<TicketModel> tickets = ticketRepository.findByUserTechUserIdIsNull();
        return checkIfListEmpty(tickets);
    }

    public List<TicketDTO> showTicketsAssigned(Long userTechId) {
        List<TicketModel> tickets = ticketRepository.findByUserTechUserId(userTechId);
        return checkIfListEmpty(tickets);
    }

    public TicketDTO showTicketById(Long id) {
        Optional<TicketModel> ticketModel = ticketRepository.findById(id);
        if (ticketModel.isEmpty()) {
            throw new EntityNotFoundException("Chamado não encontrado");
        }
        return new TicketDTO(ticketModel.get());
    }

    public TicketDTO updateTicketUser(Long id, TicketDTO updateTicket) throws MessagingException, UnsupportedEncodingException {
        Optional<TicketModel> ticketModel = ticketRepository.findById(id);

        if (ticketModel.isEmpty()) {
            throw new EntityNotFoundException("Chamado não encontrado");
        }
        ticketModel.get().setDescription(updateTicket.getDescription());
        ticketModel.get().setSubject(updateTicket.getSubject());

        ticketRepository.save(ticketModel.get());

        sendUpdatedUserTicketEmail(ticketModel.get());

        return new TicketDTO(ticketModel.get());

    }

    public TicketDTO updateTicketTech(Long id, TicketDTO updateTicket, UserDTO userTech) throws MessagingException, UnsupportedEncodingException {
        Optional<TicketModel> ticketModel = ticketRepository.findById(id);

        if (ticketModel.isEmpty()) {
            throw new EntityNotFoundException("Chamado não encontrado");
        }

        UserModel userTechModel = UserDTO.convert(userTech);

        ticketModel.get().setStatus(updateTicket.getStatus());
        ticketModel.get().setSector(updateTicket.getSector());
        ticketModel.get().setPriority(updateTicket.getPriority());
        ticketModel.get().setAnnotation(updateTicket.getAnnotation());
        ticketModel.get().setUserTech(userTechModel);
        ticketRepository.save(ticketModel.get());

        sendUpdatedTechTicketEmail(ticketModel.get());

        return new TicketDTO(ticketModel.get());
    }


    public void deleteTicket(Long id) throws MessagingException, UnsupportedEncodingException {
        Optional<TicketModel> ticketModel = ticketRepository.findById(id);

        if (ticketModel.isEmpty()) {
            throw new EntityNotFoundException("Chamado não encontrado");
        }
        sendDeletedTicketEmail(ticketModel.get());

        ticketRepository.delete(ticketModel.get());
    }


    public List<TicketDTO> showAllTickets() {
        List<TicketModel> tickets = ticketRepository.findAll();
        return checkIfListEmpty(tickets);
    }

    public List<TicketDTO> showAllTicketsTech() {
        List<TicketModel> tickets = ticketRepository.findAllByUserTechUserIdIsNotNull();
        return checkIfListEmpty(tickets);
    }

    public List<TicketDTO> showAllTicketsWithPriority(Long priorityId) {
        List<TicketModel> tickets = ticketRepository.findByPriorityPriorityId(priorityId);
        return checkIfListEmpty(tickets);
    }

    private List<TicketDTO> checkIfListEmpty(List<TicketModel> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return Collections.emptyList();
        }
        return tickets.stream().map(TicketDTO::new).collect(Collectors.toList());
    }

    private void sendEmail(String emailContent, String to, String subject) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setText(emailContent, true);
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);
    }

    private void sendNewTicketEmailTech(TicketModel ticket) throws MessagingException, UnsupportedEncodingException {
        List<UserDTO> technicians = userService.getAllTech();

        for (UserDTO tech : technicians) {
            String emailContent = "<h1>Olá, " + tech.getName() + "!</h1>"
                    + "<p>Um novo chamado foi criado e está disponível para você atender.</p>"
                    + "<ul>"
                    + "<li>Número: " + ticket.getTicketId() + "</li>"
                    + "<li>Assunto: " + ticket.getSubject() + "</li>"
                    + "<li>Descrição: " + ticket.getDescription() + "</li>"
                    + "</ul>"
                    + "<p>Equipe HelpDelas</p>";
            sendEmail(emailContent, tech.getEmail(), "Novo Chamado Disponível");
        }
    }

    private void sendNewTicketEmail(TicketModel ticketModel) throws MessagingException, UnsupportedEncodingException {
        String emailContent = "<h1>Olá, " + ticketModel.getUserBasic().getName() + "!</h1>" +
                "<p>Seu chamado foi aberto com sucesso!</p>" +
                "<ul>" +
                "<li>Número: " + ticketModel.getTicketId() + "</li>" +
                "<li>Assunto: " + ticketModel.getSubject() + "</li>" +
                "<li>Descrição: " + ticketModel.getDescription() + "</li>" +
                "</ul>" +
                "<p>Equipe HelpDelas</p>";
        sendEmail(emailContent, ticketModel.getUserBasic().getEmail(), "Novo Chamado");
    }

    private void sendUpdatedUserTicketEmail(TicketModel ticketModel) throws MessagingException, UnsupportedEncodingException {
        String emailContent = "<h1>Olá, " + ticketModel.getUserBasic().getName() + "!</h1>" +
                "<p>Seu chamado foi atualizado com sucesso!</p>" +
                "<ul>" +
                "<li>Número: " + ticketModel.getTicketId() + "</li>" +
                "<li>Assunto: " + ticketModel.getSubject() + "</li>" +
                "<li>Descrição: " + ticketModel.getDescription() + "</li>" +
                "</ul>" +
                "<p>Equipe HelpDelas</p>";
        sendEmail(emailContent, ticketModel.getUserBasic().getEmail(), "Chamado Atualizado Com Sucesso");
    }

    private void sendUpdatedTechTicketEmail(TicketModel ticketModel) throws MessagingException, UnsupportedEncodingException {
        String emailContent = "<h1>Olá, " + ticketModel.getUserBasic().getName() + "!</h1>" +
                "<p>Seu chamado tem uma nova atualização!</p>" +
                "<ul>" +
                "<li>Número: " + ticketModel.getTicketId() + "</li>" +
                "<li>Assunto: " + ticketModel.getSubject() + "</li>" +
                "<li>Descrição: " + ticketModel.getDescription() + "</li>" +
                "<li>Status: " + ticketModel.getStatus().getDescription() + "</li>" +
                "<li>Prioridade: " + ticketModel.getPriority().getNamePriority() + "</li>" +
                "</ul>" +
                "<p>Equipe HelpDelas</p>";

        String emailSubject = "Chamado nº " + ticketModel.getTicketId() + " - Atualizado";
        sendEmail(emailContent, ticketModel.getUserBasic().getEmail(), emailSubject);
    }

    private void sendDeletedTicketEmail(TicketModel ticketModel) throws MessagingException, UnsupportedEncodingException {
        String emailContent = "<h1>Olá, " + ticketModel.getUserBasic().getName() + "!</h1>" +
                "<p>Seu chamado nº " + ticketModel.getTicketId() + " foi deletado com sucesso!</p>" +
                "<p>Equipe HelpDelas</p>";
        sendEmail(emailContent, ticketModel.getUserBasic().getEmail(), "Chamado Deletado Com Sucesso");
    }

}
