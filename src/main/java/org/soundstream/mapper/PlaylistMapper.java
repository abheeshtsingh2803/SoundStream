package org.soundstream.mapper;

import org.soundstream.dto.response.PlaylistResponseDTO;
import org.soundstream.model.Playlist;
import org.soundstream.model.Song;

import java.util.stream.Collectors;

public class PlaylistMapper {

    public static PlaylistResponseDTO toDto(Playlist playlist) {

        PlaylistResponseDTO dto = new PlaylistResponseDTO();
        dto.setId(playlist.getPlaylistId());
        dto.setName(playlist.getPlaylistName());

        dto.setSongs(
                playlist.getSongs()
                        .stream()
                        .map(Song::getSongName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
