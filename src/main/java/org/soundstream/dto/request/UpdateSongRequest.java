package org.soundstream.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateSongRequest {

    private String title;

    @Positive(message = "Duration must be greater than zero")
    private Integer duration;

    private Long albumId;

    private Set<Long> artistIds;
}