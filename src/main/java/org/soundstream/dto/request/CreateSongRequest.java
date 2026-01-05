package org.soundstream.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Set;

@Data
public class CreateSongRequest {

    @NotBlank
    private String title;

    @Positive
    private int duration;

    @NotNull
    private Long albumId;

    @NotNull
    private Set<Long> artistIds;
}
