package com.ticket.server.controller;

import com.ticket.server.dto.TicketResponseDTO;
import java.util.stream.Collectors;

import com.ticket.server.entity.*;
import com.ticket.server.security.JwtUtil;
import com.ticket.server.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;
    private final TicketCategoryService categoryService;
    private final JwtUtil jwtUtil;

    public TicketController(
            TicketService ticketService,
            UserService userService,
            TicketCategoryService categoryService,
            JwtUtil jwtUtil) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public TicketResponseDTO createTicket(@RequestBody Ticket ticket) {

        ticket.setStatus(TicketStatus.NEW);
        Ticket savedTicket = ticketService.createTicket(ticket);

        return ticketService.mapToDTO(savedTicket);
    }

    @GetMapping
    public List<TicketResponseDTO> getAllTickets(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);

        String role = jwtUtil.extractRole(token);
        String email = jwtUtil.extractEmail(token);

        // ADMIN → all tickets
        if (role.equals("ADMIN")) {
            return ticketService.getAllTickets()
                    .stream()
                    .map(ticketService::mapToDTO)
                    .toList();
        }

        // USER → only own tickets
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketService.getTicketsByCreator(user)
                .stream()
                .map(ticketService::mapToDTO)
                .toList();
    }

    @GetMapping("/created-by/{userId}")
    public List<TicketResponseDTO> getTicketsCreatedBy(@PathVariable Long userId) {

        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketService.getTicketsByCreator(user)
                .stream()
                .map(ticketService::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Tickets by Assignee
    @GetMapping("/assigned-to/{userId}")
    public List<TicketResponseDTO> getTicketsAssignedTo(@PathVariable Long userId) {

        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketService.getTicketsByAssignee(user)
                .stream()
                .map(ticketService::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Ticket Status
    @PutMapping("/{ticketId}/status")
    public TicketResponseDTO updateStatus(
            @PathVariable Long ticketId,
            @RequestParam TicketStatus status) {

        Ticket ticket = ticketService.getTicketById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(status);
        Ticket updatedTicket = ticketService.createTicket(ticket);

        return ticketService.mapToDTO(updatedTicket);
    }

    @PutMapping("/{ticketId}/assign/{userId}")
    public TicketResponseDTO assignTicket(
            @PathVariable Long ticketId,
            @PathVariable Long userId) {
        Ticket assignedTicket = ticketService.assignTicket(ticketId, userId);
        return ticketService.mapToDTO(assignedTicket);
    }

    @PutMapping("/{ticketId}/status/{status}")
    public TicketResponseDTO updateTicketStatus(
            @PathVariable Long ticketId,
            @PathVariable TicketStatus status) {
        Ticket updatedTicket = ticketService.updateTicketStatus(ticketId, status);
        return ticketService.mapToDTO(updatedTicket);
    }

    @GetMapping("/my-created/{userId}")
    public List<TicketResponseDTO> myCreatedTickets(@PathVariable Long userId) {
        return ticketService.getTicketsCreatedByUserId(userId)
                .stream()
                .map(ticketService::mapToDTO)
                .toList();
    }

    @GetMapping("/my-assigned/{userId}")
    public List<TicketResponseDTO> myAssignedTickets(@PathVariable Long userId) {
        return ticketService.getTicketsAssignedToUserId(userId)
                .stream()
                .map(ticketService::mapToDTO)
                .toList();
    }

}
