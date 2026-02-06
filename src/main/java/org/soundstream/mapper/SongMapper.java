package org.soundstream.mapper;

import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.model.Artists;
import org.soundstream.model.Songs;

import java.util.stream.Collectors;

public class SongMapper {

    public static SongResponseDTO toDto(Songs songs) {

        SongResponseDTO dto = new SongResponseDTO();
        dto.setId(songs.getSongID());
        dto.setTitle(songs.getSongName());
        dto.setDuration(songs.getDuration());
        dto.setAlbumName(songs.getAlbums().getAlbumName());

        dto.setArtists(
                songs.getArtists()
                        .stream()
                        .map(Artists::getArtistName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
