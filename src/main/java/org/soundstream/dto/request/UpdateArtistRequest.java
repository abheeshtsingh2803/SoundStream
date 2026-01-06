package org.soundstream.dto.request;

import lombok.Data;
import org.soundstream.enum_.Genre;

@Data
public class UpdateArtistRequest {

    private String name;

    private Genre genre;
}
