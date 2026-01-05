package org.soundstream.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.soundstream.enum_.Genre;

@Data
public class CreateArtistRequest {
    @NotBlank
    private String name;

    @NotNull
    private Genre genre;
}
