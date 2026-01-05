package org.soundstream.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class PlaylistResponseDTO {
    private Long id;
    private String name;
    private Set<String> songs;
}
