package com.ticket.server.service;

import com.ticket.server.dto.TicketResponseDTO;
import com.ticket.server.entity.Ticket;
import com.ticket.server.entity.TicketStatus;
import com.ticket.server.entity.User;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    Ticket createTicket(Ticket ticket);

    Optional<Ticket> getTicketById(Long ticketId);

    List<Ticket> getAllTickets();

    List<Ticket> getTicketsByCreator(User user);

    List<Ticket> getTicketsByAssignee(User user);

    List<Ticket> getTicketsByStatus(TicketStatus status);

    // DTO mapping method
    TicketResponseDTO mapToDTO(Ticket ticket);

    Ticket assignTicket(Long ticketId, Long userId);

    Ticket updateTicketStatus(Long ticketId, TicketStatus status);

    List<Ticket> getTicketsCreatedByUserId(Long userId);

    List<Ticket> getTicketsAssignedToUserId(Long userId);

    List<TicketResponseDTO> getAllTicketsDTO();

    List<TicketResponseDTO> getTicketsByCreatorDTO(User user);

}
