package org.example.service;

import org.example.exceptions.ResourceNotFoundException;
import org.example.model.Playlist;
import org.example.model.Song;
import org.example.repository.PlaylistRepository;
import org.example.repository.SongRepository;

import java.util.List;
import java.util.Set;

public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public Playlist getPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("PLayList not found!"));
    }

    @Override
    public Playlist getPlaylistByName(String name) {
        return playlistRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("PLayList not found!"));
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist createPlaylist(String name) {
        return playlistRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Playlist playlist = new Playlist();
                    playlist.setPlaylistName(name);
                    return playlistRepository.save(playlist);
                });
    }

    @Override
    public Playlist updatePlaylist(Long playlistId, Playlist playlist) {
        Playlist existing = getPlaylistById(playlistId);
        existing.setPlaylistName(existing.getPlaylistName());

        return playlistRepository.save(existing);
    }

    @Override
    public void deletePlaylistById(Long playlistId) {
        if (!playlistRepository.existsById(playlistId)) {
            throw new ResourceNotFoundException("Playlist not found with id " + playlistId);
        }
        playlistRepository.deleteById(playlistId);
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = getPlaylistById(playlistId);
        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Song not found with id " + songId));

        playlist.getSongs().add(song);

        playlistRepository.save(playlist);
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = getPlaylistById(playlistId);
        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Song not found with id " + songId));

        playlist.getSongs().remove(song);

        playlistRepository.save(playlist);
    }

    @Override
    public Set<Song> getSongsByPlaylistId(Long playlistId) {
        Playlist playlist = getPlaylistById(playlistId);
        return playlist.getSongs();
    }
}
