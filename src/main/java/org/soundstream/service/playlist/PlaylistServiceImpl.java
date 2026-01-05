package org.soundstream.service.playlist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soundstream.dto.request.CreatePlaylistRequest;
import org.soundstream.dto.response.PlaylistResponseDTO;
import org.soundstream.exception.ResourceNotFoundException;
import org.soundstream.mapper.PlaylistMapper;
import org.soundstream.model.Playlist;
import org.soundstream.model.Song;
import org.soundstream.repository.PlaylistRepository;
import org.soundstream.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    @Override
    public Playlist getPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("PLayList not found!"));
    }

    @Override
    public Playlist getPlaylistByName(String playlistName) {
        return playlistRepository.findByPlaylistNameIgnoreCase(playlistName)
                .orElseThrow(() -> new ResourceNotFoundException("PLayList not found!"));
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public PlaylistResponseDTO createPlaylist(CreatePlaylistRequest request) {

        log.info("Creating playlist: {}", request.getName());

        Playlist playlist = new Playlist();
        playlist.setPlaylistName(request.getName());

        Playlist saved = playlistRepository.save(playlist);

        return PlaylistMapper.toDto(saved);
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

    @Transactional
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
