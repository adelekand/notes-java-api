package com.adelekand.speer.repository;

import com.adelekand.speer.exception.NoteNotFoundException;
import com.adelekand.speer.model.Note;
import com.adelekand.speer.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomNoteRepository {
    List<Note> findByCreatorAndByRegexpName(User userId, String titleRegex);

    Note findByIdAndCreatorOrSharedWith(String id, User userId) throws NoteNotFoundException;
}
