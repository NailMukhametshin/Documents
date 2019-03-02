package ru.itpark.documents.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itpark.documents.dto.DocumentDto;
import ru.itpark.documents.service.DocumentService;

@Controller
@RequestMapping("/")
public class DocumentController {
    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("items", service.getAll());
        return "all";
    }


    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("item", service.getById(id));

        return "view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("item", service.getByIdOrEmpty(id));
        return "edit";
    }

    @PreAuthorize("hasAuthority('EDIT')")
    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable int id,
            @ModelAttribute DocumentDto dto
    ) {
        service.save(dto);

        return "redirect:/";
    }

    @GetMapping("/{id}/remove")
    public String remove(
            @PathVariable int id,
            Model model
    ) {
        model.addAttribute("item", service.getById(id));
        return "remove";
    }

    @PreAuthorize("hasAuthority('REMOVE')")
    @PostMapping("/{id}/remove")
    public String remove(
            @PathVariable int id
    ) {
        service.removeById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/search", params = "documentName")
    public String search(@RequestParam String documentName, Model model) {
        model.addAttribute("documentName", documentName);
        model.addAttribute("items", service.findByName(documentName));
        return "all";
    }

}