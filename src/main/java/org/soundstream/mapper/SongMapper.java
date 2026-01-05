package org.soundstream.mapper;

import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;

import java.util.stream.Collectors;

public class SongMapper {

    public static SongResponseDTO toDto(Song song) {

        SongResponseDTO dto = new SongResponseDTO();
        dto.setId(song.getSongID());
        dto.setTitle(song.getSongName());
        dto.setDuration(song.getDuration());
        dto.setAlbumName(song.getAlbum().getAlbumName());

        dto.setArtists(
                song.getArtists()
                        .stream()
                        .map(Artist::getArtistName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
