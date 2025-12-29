package com.ticket.server.controller;

import com.ticket.server.entity.TicketCategory;
import com.ticket.server.service.TicketCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketCategoryController {

    private final TicketCategoryService categoryService;

    public TicketCategoryController(TicketCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create category
    @PostMapping
    public TicketCategory createCategory(@RequestBody TicketCategory category) {
        return categoryService.createCategory(category);
    }

    // Get all categories
    @GetMapping
    public List<TicketCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
