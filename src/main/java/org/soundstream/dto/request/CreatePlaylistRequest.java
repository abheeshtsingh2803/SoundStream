package org.soundstream.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class CreatePlaylistRequest {
    @NotBlank
    private String name;
}
