package org.example.service;

import org.example.model.Playlist;
import org.example.model.Song;

import java.util.List;
import java.util.Set;

public interface PlaylistService {
    Playlist getPlaylistById(Long playlistId);

    Playlist getPlaylistByName(String name);

    List<Playlist> getAllPlaylists();

    Playlist createPlaylist(String name);

    Playlist updatePlaylist(Long playlistId, Playlist playlist);

    void deletePlaylistById(Long playlistId);

    void addSongToPlaylist(Long playlistId, Long songId);

    void removeSongFromPlaylist(Long playlistId, Long songId);

    Set<Song> getSongsByPlaylistId(Long playlistId);
}
