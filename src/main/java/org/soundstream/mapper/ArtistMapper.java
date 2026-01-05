package org.soundstream.mapper;

import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;

import java.util.stream.Collectors;

public class ArtistMapper {
    public static ArtistResponseDTO toDto(Artist artist) {

        ArtistResponseDTO dto = new ArtistResponseDTO();
        dto.setId(artist.getArtistId());
        dto.setName(artist.getArtistName());
        dto.setGenre(artist.getGenre().name());

        dto.setSongs(
                artist.getSongs()
                        .stream()
                        .map(Song::getSongName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
