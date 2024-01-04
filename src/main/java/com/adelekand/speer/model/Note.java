package com.adelekand.speer.model;

import com.adelekand.speer.dto.response.NoteResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document("notes")
public class Note {
    @Id
    private String id;

    private String title;

    private String content;

    @DocumentReference(lazy = true)
    private User creator;

    @DocumentReference(lazy = true)
    private User sharedWith;

    @CreatedDate
    protected LocalDateTime createdAt;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Version
    Long version;

    public NoteResponse toResponse() {
        return NoteResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }
}
