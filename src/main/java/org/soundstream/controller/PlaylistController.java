package org.soundstream.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soundstream.dto.request.CreatePlaylistRequest;
import org.soundstream.dto.response.PlaylistResponseDTO;
import org.soundstream.model.Playlist;
import org.soundstream.model.Song;
import org.soundstream.service.playlist.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable Long id) {
        return playlistService.getPlaylistById(id);
    }

    @GetMapping("/name/{name}")
    public Playlist getPlaylistByName(@PathVariable String name) {
        return playlistService.getPlaylistByName(name);
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistResponseDTO createPlaylist(@RequestBody @Valid CreatePlaylistRequest request) {
        return playlistService.createPlaylist(request);
    }

    @PutMapping("/{id}")
    public Playlist updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlist) {
        return playlistService.updatePlaylist(id, playlist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylistById(id);
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public void addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        playlistService.addSongToPlaylist(playlistId, songId);
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public void removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        playlistService.removeSongFromPlaylist(playlistId, songId);
    }

    @GetMapping("/{id}/songs")
    public Set<Song> getSongsByPlaylistId(@PathVariable Long id) {
        return playlistService.getSongsByPlaylistId(id);
    }
}