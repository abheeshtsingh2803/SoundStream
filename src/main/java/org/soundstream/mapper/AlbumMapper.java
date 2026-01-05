package org.soundstream.mapper;

import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.model.Album;
import org.soundstream.model.Song;

import java.util.stream.Collectors;

public class AlbumMapper {

    public static AlbumResponseDTO toDto(Album album) {

        AlbumResponseDTO dto = new AlbumResponseDTO();
        dto.setId(album.getAlbumId());
        dto.setName(album.getAlbumName());
        dto.setReleaseYear(album.getReleaseYear());

        dto.setSongs(
                album.getSongs()
                        .stream()
                        .map(Song::getSongName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
