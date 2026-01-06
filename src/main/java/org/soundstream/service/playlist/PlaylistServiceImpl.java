package org.soundstream.service.playlist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soundstream.dto.request.CreatePlaylistRequest;
import org.soundstream.dto.request.UpdatePlaylistRequest;
import org.soundstream.dto.response.PlaylistResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.exception.ResourceAlreadyExistsException;
import org.soundstream.exception.ResourceNotFoundException;
import org.soundstream.mapper.PlaylistMapper;
import org.soundstream.mapper.SongMapper;
import org.soundstream.model.Playlist;
import org.soundstream.model.Song;
import org.soundstream.repository.PlaylistRepository;
import org.soundstream.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    @Override
    @Transactional(readOnly = true)
    public PlaylistResponseDTO getPlaylistById(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Playlist with id " + playlistId + " not found"
                ));

        log.info("Playlist found with id " + playlistId);

        return PlaylistMapper.toDto(playlist);
    }

    @Override
    @Transactional(readOnly = true)
    public PlaylistResponseDTO getPlaylistByName(String playlistName) {
        Playlist playlist = playlistRepository.findByPlaylistNameIgnoreCase(playlistName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "PLayList not found!"
                ));

        log.info("Playlist found with name " + playlistName);

        return PlaylistMapper.toDto(playlist);
    }

    @Override
    public Page<PlaylistResponseDTO> getAllPlaylists(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("playlistName").ascending());

        Page<Playlist> playlists = playlistRepository.findAll(pageable);

        return playlists.map(PlaylistMapper::toDto);
    }

    @Override
    @Transactional
    public PlaylistResponseDTO createPlaylist(CreatePlaylistRequest request) {

        String playlistName = request.getName().trim();

        log.info("Attempting to create playlist with name='{}'", playlistName);

        boolean exists = playlistRepository
                .existsByPlaylistNameIgnoreCase(playlistName);

        if (exists) {
            log.warn("Playlist creation failed. Playlist already exists with name='{}'", playlistName);
            throw new ResourceAlreadyExistsException(
                    "Playlist already exists with name: " + playlistName
            );
        }

        Playlist playlist = new Playlist();
        playlist.setPlaylistName(playlistName);

        Playlist saved = playlistRepository.save(playlist);

        log.info("Playlist created successfully with id={}, name='{}'",
                saved.getPlaylistId(), playlistName);

        return PlaylistMapper.toDto(saved);
    }


    @Override
    @Transactional
    public PlaylistResponseDTO updatePlaylist(Long playlistId, UpdatePlaylistRequest request) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + playlistId
                        )
                );

        if (request.getName() != null) {
            String newName = request.getName().trim();

            boolean exists = playlistRepository
                    .existsByPlaylistNameIgnoreCase(newName);

            if (exists && !playlist.getPlaylistName().equalsIgnoreCase(newName)) {
                throw new ResourceAlreadyExistsException(
                        "Playlist already exists with name: " + newName
                );
            }

            playlist.setPlaylistName(newName);
        }

        Playlist saved = playlistRepository.save(playlist);

        log.info("Playlist updated successfully with id={}", playlistId);

        return PlaylistMapper.toDto(saved);
    }


    @Override
    public void deletePlaylistById(Long playlistId) {
        if (!playlistRepository.existsById(playlistId)) {
            throw new ResourceNotFoundException("Playlist not found with id " + playlistId);
        }
        playlistRepository.deleteById(playlistId);
        log.info("Playlist deleted successfully with id={}", playlistId);
    }

    @Override
    @Transactional
    public PlaylistResponseDTO addSongsToPlaylist(Long playlistId, Set<Long> songIds) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + playlistId
                        )
                );

        log.info("Adding {} song(s) to playlistId={}", songIds.size(), playlistId);

        Set<Song> existingSongs = playlist.getSongs();

        Set<Song> songsToAdd = songIds.stream()
                .map(songId -> songRepository.findById(songId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Song not found with id: " + songId
                                )
                        )
                )
                .filter(song -> !existingSongs.contains(song)) // prevent duplicates
                .collect(Collectors.toSet());

        if (songsToAdd.isEmpty()) {
            log.warn("No new songs to add for playlistId={}", playlistId);
            return PlaylistMapper.toDto(playlist);
        }

        existingSongs.addAll(songsToAdd);

        Playlist saved = playlistRepository.save(playlist);

        log.info("Successfully added {} song(s) to playlistId={}",
                songsToAdd.size(), playlistId);

        return PlaylistMapper.toDto(saved);
    }


    @Override
    @Transactional
    public PlaylistResponseDTO removeSongFromPlaylist(Long playlistId, Long songId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + playlistId
                        )
                );

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: " + songId
                        )
                );

        boolean removed = playlist.getSongs().remove(song);

        if (!removed) {
            throw new ResourceNotFoundException(
                    "Song with id " + songId + " not found in playlist " + playlistId
            );
        }

        Playlist saved = playlistRepository.save(playlist);

        log.info("Removed songId={} from playlistId={}", songId, playlistId);

        return PlaylistMapper.toDto(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public Set<SongResponseDTO> getSongsByPlaylistId(Long playlistId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Playlist not found with id: " + playlistId)
                );

        log.info("Fetching songs for playlistId={}", playlistId);

        return playlist.getSongs()
                .stream()
                .map(SongMapper::toDto)
                .collect(Collectors.toSet());
    }

}
