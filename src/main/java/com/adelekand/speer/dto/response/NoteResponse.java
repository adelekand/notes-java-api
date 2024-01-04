package com.adelekand.speer.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class NoteResponse {
    private String id;

    private String title;

    private String content;
}
