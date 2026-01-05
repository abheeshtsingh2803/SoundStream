package org.soundstream.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class ArtistResponseDTO {

    private Long id;
    private String name;
    private String genre;
    private Set<String> songs;

}
