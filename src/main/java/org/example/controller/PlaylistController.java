package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Playlist;
import org.example.model.Song;
import org.example.service.PlaylistService;
import org.example.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylistById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(playlistService.getPlaylistById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getPlaylistByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(playlistService.getPlaylistByName(name));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestParam String name) {
        try {
            Playlist playlist = playlistService.createPlaylist(name);
            return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlaylist(
            @PathVariable Long id,
            @RequestBody Playlist playlist
    ) {
        try {
            return ResponseEntity.ok(playlistService.updatePlaylist(id, playlist));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        try {
            playlistService.deletePlaylistById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<?> addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        try {
            playlistService.addSongToPlaylist(playlistId, songId);
            return ResponseEntity.ok("Song added to playlist");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<?> removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        try {
            playlistService.removeSongFromPlaylist(playlistId, songId);
            return ResponseEntity.ok("Song removed from playlist");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<?> getSongsByPlaylistId(@PathVariable Long id) {
        try {
            Set<Song> songs = playlistService.getSongsByPlaylistId(id);
            return ResponseEntity.ok(songs);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
