package com.ticket.server.repository;

import com.ticket.server.entity.Ticket;
import com.ticket.server.entity.TicketStatus;
import com.ticket.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Get tickets created by a user
    List<Ticket> findByCreatedBy(User user);

    // Get tickets assigned to a user
    List<Ticket> findByAssignedTo(User user);

    // Get tickets by status
    List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByCreatedBy_UserId(Long userId);

    List<Ticket> findByAssignedTo_UserId(Long userId);

}
