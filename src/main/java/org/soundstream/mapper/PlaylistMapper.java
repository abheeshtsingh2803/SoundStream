package org.soundstream.mapper;

import org.soundstream.dto.response.PlaylistResponseDTO;
import org.soundstream.model.Playlists;
import org.soundstream.model.Songs;

import java.util.stream.Collectors;

public class PlaylistMapper {

    public static PlaylistResponseDTO toDto(Playlists playlists) {

        PlaylistResponseDTO dto = new PlaylistResponseDTO();
        dto.setId(playlists.getPlaylistId());
        dto.setName(playlists.getPlaylistName());

        dto.setSongs(
                playlists.getSongs()
                        .stream()
                        .map(Songs::getSongName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
