package com.ticket.server.repository;

import com.ticket.server.entity.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {

    Optional<TicketCategory> findByCategoryName(String categoryName);
}
