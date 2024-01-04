package com.adelekand.speer.service;

import com.adelekand.speer.dto.request.NoteRequest;
import com.adelekand.speer.dto.request.ShareNoteRequest;
import com.adelekand.speer.dto.response.NoteResponse;
import com.adelekand.speer.exception.NoteNotFoundException;
import com.adelekand.speer.model.Note;
import com.adelekand.speer.repository.CustomNoteRepository;
import com.adelekand.speer.repository.NoteRepository;
import com.adelekand.speer.repository.UserRepository;
import com.adelekand.speer.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final SecurityUtils utils;

    public List<NoteResponse> getUserNotes() {
        var user = utils.getUser();
        return noteRepository.findByCreatorOrSharedWith(user, user)
                .stream().map(Note::toResponse)
                .collect(Collectors.toList());
    }

    public NoteResponse getUserNoteById(String id) throws NoteNotFoundException {
        var user = utils.getUser();

        return noteRepository.findByIdAndCreatorOrSharedWith(id, user, user)
                .orElseThrow(() -> new NoteNotFoundException(id)).toResponse();
    }

    public NoteResponse createNote(NoteRequest request) {
        var user = utils.getUser();
        var newNote = Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .creator(user)
                .build();

        return noteRepository.save(newNote).toResponse();
    }

    public NoteResponse editNote(String id, NoteRequest request) throws NoteNotFoundException {
        var user = utils.getUser();
        var noteToUpdated = noteRepository.findByIdAndCreatorOrSharedWith(id, user, user)
                .orElseThrow(() -> new NoteNotFoundException(id));

        noteToUpdated.setTitle(request.getTitle());
        noteToUpdated.setContent(request.getContent());

        return noteRepository.save(noteToUpdated).toResponse();
    }

    public Boolean deleteUserNoteById(String id) throws NoteNotFoundException {
        var user = utils.getUser();
        var noteToDelete = noteRepository.findByIdAndCreatorOrSharedWith(id, user, user)
                .orElseThrow(() -> new NoteNotFoundException(id));

        noteRepository.deleteById(noteToDelete.getId());
        return true;
    }

    public List<NoteResponse> getUserNotesBySearchCriteria(String query) {
        var user = utils.getUser();
        return noteRepository.findByCreatorAndByRegexpName(user, query)
                .stream().map(Note::toResponse)
                .collect(Collectors.toList());
    }

    public void shareNote(String id, ShareNoteRequest request) throws NoteNotFoundException {
        var user = utils.getUser();
        var userToShareTo = userRepository.findByUsername(request.getUsername()).orElseThrow();

        var noteToShare = noteRepository.findByIdAndCreatorOrSharedWith(id, user, user)
                .orElseThrow(() -> new NoteNotFoundException(id));
        noteToShare.setSharedWith(userToShareTo);
        noteRepository.save(noteToShare);
    }
}
