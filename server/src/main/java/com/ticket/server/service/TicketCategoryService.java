package com.ticket.server.service;

import com.ticket.server.entity.TicketCategory;

import java.util.List;
import java.util.Optional;

public interface TicketCategoryService {

    TicketCategory createCategory(TicketCategory category);

    Optional<TicketCategory> getCategoryById(Long categoryId);

    Optional<TicketCategory> getCategoryByName(String categoryName);

    List<TicketCategory> getAllCategories();
}
