package org.soundstream.mapper;

import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.model.Artists;
import org.soundstream.model.Songs;

import java.util.stream.Collectors;

public class ArtistMapper {
    public static ArtistResponseDTO toDto(Artists artists) {

        ArtistResponseDTO dto = new ArtistResponseDTO();
        dto.setId(artists.getArtistId());
        dto.setName(artists.getArtistName());
        dto.setGenre(artists.getGenre().name());

        dto.setSongs(
                artists.getSongs()
                        .stream()
                        .map(Songs::getSongName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
