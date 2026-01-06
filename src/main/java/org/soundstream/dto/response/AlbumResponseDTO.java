package org.soundstream.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class AlbumResponseDTO {
    private Long id;
    private String name;
    private Integer releaseYear;
    private Set<String> songs;
}
