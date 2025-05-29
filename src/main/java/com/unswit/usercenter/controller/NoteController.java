package com.unswit.usercenter.controller;

import com.unswit.usercenter.model.domain.Notes;
import com.unswit.usercenter.service.NotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/notes")
public class NoteController {
    @Resource
    private NotesService notesService;
    @GetMapping()
    public String notes(@RequestParam Notes notes) {
        notesService.addNotes(notes);
        return "success";
    }
}
