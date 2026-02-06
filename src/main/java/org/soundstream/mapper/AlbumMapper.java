package org.soundstream.mapper;

import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.model.Albums;
import org.soundstream.model.Songs;

import java.util.stream.Collectors;

public class AlbumMapper {

    public static AlbumResponseDTO toDto(Albums albums) {

        AlbumResponseDTO dto = new AlbumResponseDTO();
        dto.setId(albums.getAlbumId());
        dto.setName(albums.getAlbumName());
        dto.setReleaseYear(albums.getReleaseYear());

        dto.setSongs(
                albums.getSongs()
                        .stream()
                        .map(Songs::getSongName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
