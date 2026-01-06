package org.soundstream.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateAlbumRequest {

    private String name;

    @Positive(message = "Duration must be greater than zero")
    private Integer releaseYear;

    private Long artistId;
}
