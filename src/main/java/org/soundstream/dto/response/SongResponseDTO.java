package org.soundstream.dto.response;

import lombok.*;

import java.util.Set;

@Data
public class SongResponseDTO {
    private Long id;
    private String title;
    private Integer duration;
    private String albumName;
    private Set<String> artists;
}
