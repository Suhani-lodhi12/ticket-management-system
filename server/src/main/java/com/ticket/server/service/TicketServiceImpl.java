package com.ticket.server.service;

import com.ticket.server.dto.TicketResponseDTO;
import com.ticket.server.entity.Ticket;
import com.ticket.server.entity.TicketStatus;
import com.ticket.server.entity.User;
import com.ticket.server.repository.TicketRepository;
import com.ticket.server.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(
            TicketRepository ticketRepository,
            UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> getTicketsByCreator(User user) {
        return ticketRepository.findByCreatedBy(user);
    }

    @Override
    public List<Ticket> getTicketsByAssignee(User user) {
        return ticketRepository.findByAssignedTo(user);
    }

    @Override
    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    // ---------------- DTO MAPPING ----------------

    @Override
    public TicketResponseDTO mapToDTO(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();

        dto.setTicketId(ticket.getTicketId());
        dto.setTitle(ticket.getTitle());
        dto.setStatus(ticket.getStatus().name());
        dto.setPriority(ticket.getPriority().name());
        dto.setCategory(ticket.getCategory().getCategoryName());
        dto.setCreatedBy(ticket.getCreatedBy().getFullName());

        dto.setAssignedTo(
                ticket.getAssignedTo() != null
                        ? ticket.getAssignedTo().getFullName()
                        : null);

        return dto;
    }

    @Override
    public Ticket assignTicket(Long ticketId, Long userId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ticket.setAssignedTo(user);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket updateTicketStatus(Long ticketId, TicketStatus status) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getTicketsCreatedByUserId(Long userId) {
        return ticketRepository.findByCreatedBy_UserId(userId);
    }

    @Override
    public List<Ticket> getTicketsAssignedToUserId(Long userId) {
        return ticketRepository.findByAssignedTo_UserId(userId);
    }

    @Override
    public List<TicketResponseDTO> getAllTicketsDTO() {
        return ticketRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<TicketResponseDTO> getTicketsByCreatorDTO(User user) {
        return ticketRepository.findByCreatedBy(user)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

}
