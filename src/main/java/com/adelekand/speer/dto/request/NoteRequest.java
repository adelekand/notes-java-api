package com.adelekand.speer.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class NoteRequest {
    private String title;

    private String content;
}
