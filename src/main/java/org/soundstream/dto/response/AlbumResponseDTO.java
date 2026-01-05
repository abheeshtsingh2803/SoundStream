package org.soundstream.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class AlbumResponseDTO {
    private Long id;
    private String name;
    private int releaseYear;
    private Set<String> songs;
}
