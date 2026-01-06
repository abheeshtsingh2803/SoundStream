package org.soundstream.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class AddSongsToPlaylistRequest {

    @NotEmpty(message = "Song IDs must not be empty")
    private Set<Long> songIds;

}
