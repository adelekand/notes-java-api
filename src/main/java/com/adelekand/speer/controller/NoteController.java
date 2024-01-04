package com.adelekand.speer.controller;

import com.adelekand.speer.dto.request.NoteRequest;
import com.adelekand.speer.dto.request.ShareNoteRequest;
import com.adelekand.speer.dto.response.NoteResponse;
import com.adelekand.speer.exception.NoteNotFoundException;
import com.adelekand.speer.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/notes")
    public List<NoteResponse> getNotes() {
        return noteService.getUserNotes();
    }

    @GetMapping("/notes/{id}")
    public NoteResponse getNote(@PathVariable("id") String id) throws NoteNotFoundException {

        return noteService.getUserNoteById(id);
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteRequest request) {
        var response =  noteService.createNote(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/notes/{id}")
    public NoteResponse editNote(
            @PathVariable("id") String id,
            @RequestBody NoteRequest request
    ) throws NoteNotFoundException {
        return noteService.editNote(id, request);
    }

    @DeleteMapping("/notes/{id}")
    public Boolean deleteNote(@PathVariable("id") String id) throws NoteNotFoundException {

        return noteService.deleteUserNoteById(id);
    }

    @PostMapping("/notes/{id}/share")
    public String shareNote(
            @PathVariable("id") String id,
            @RequestBody ShareNoteRequest request
    ) throws NoteNotFoundException {
        noteService.shareNote(id, request);
        return "Note has been shared";
    }

    @GetMapping("/search")
    public List<NoteResponse> searchNotes(@RequestParam(name = "query") String query) {
        return noteService.getUserNotesBySearchCriteria(query);
    }
}
