package org.soundstream.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
public class CreateAlbumRequest {
    @NotBlank
    private String name;

    @Positive
    private int releaseYear;

    private Long artistId;
}
