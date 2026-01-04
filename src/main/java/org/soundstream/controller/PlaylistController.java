package org.soundstream.controller;

import lombok.RequiredArgsConstructor;
import org.soundstream.model.Playlist;
import org.soundstream.model.Song;
import org.soundstream.service.playlist.PlaylistService;
import org.soundstream.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/playlists")
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
    public Playlist createPlaylist(@RequestParam String name) {
        return playlistService.createPlaylist(name);
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