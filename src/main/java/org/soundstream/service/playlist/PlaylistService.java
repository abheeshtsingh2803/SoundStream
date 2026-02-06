package org.soundstream.service.playlist;

import org.soundstream.dto.request.CreatePlaylistRequest;
import org.soundstream.dto.request.UpdatePlaylistRequest;
import org.soundstream.dto.response.PlaylistResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface PlaylistService {
    PlaylistResponseDTO getPlaylistById(Long playlistId);

    PlaylistResponseDTO getPlaylistByName(String name);

    Page<PlaylistResponseDTO> getAllPlaylists(int page, int size);

    PlaylistResponseDTO createPlaylist(CreatePlaylistRequest request);

    PlaylistResponseDTO updatePlaylist(Long playlistId, UpdatePlaylistRequest request);

    void deletePlaylistById(Long playlistId);

    PlaylistResponseDTO addSongsToPlaylist(Long playlistId, Set<Long> songIds);

    PlaylistResponseDTO removeSongFromPlaylist(Long playlistId, Long songId);

    Set<SongResponseDTO> getSongsByPlaylistId(Long playlistId);

}
