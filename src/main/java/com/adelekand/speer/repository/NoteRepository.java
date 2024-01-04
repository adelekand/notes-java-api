package com.adelekand.speer.repository;

import com.adelekand.speer.model.Note;
import com.adelekand.speer.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends MongoRepository<Note, String>, CustomNoteRepository {
    List<Note> findByCreatorOrSharedWith(User creator, User sharedWith);
}
