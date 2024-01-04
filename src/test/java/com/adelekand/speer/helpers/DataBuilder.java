package com.adelekand.speer.helpers;

import com.adelekand.speer.enums.ERole;
import com.adelekand.speer.model.Note;
import com.adelekand.speer.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataBuilder {

    public List<User> buildUsers(List<String[]> usersToBuild) {
        return usersToBuild.stream()
                .map(user -> User.builder()
                        .username(user[0])
                        .firstName(user[1])
                        .lastName(user[2])
                        .role(ERole.USER)
                        .password(user[3])
                        .build()
                )
                .toList();
    }

    public List<Note> buildNotes(List<Object[]> notesToBuild) {
        return notesToBuild.stream()
                .map(note -> Note.builder()
                        .title(note[0].toString())
                        .content(note[1].toString())
                        .creator((User) note[2])
                        .build()
                )
                .toList();
    }
}
