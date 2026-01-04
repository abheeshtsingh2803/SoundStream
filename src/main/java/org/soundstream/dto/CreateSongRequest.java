package org.soundstream.dto;

import lombok.Data;
import org.soundstream.enums.Genre;

@Data
public class CreateSongRequest {
    private String title;
    private String artistName;
    private Genre genre;
    private String albumName;
    private int duration;
}
