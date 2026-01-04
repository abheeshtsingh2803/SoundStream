package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateSongRequest;
import org.example.model.Album;
import org.example.model.Artist;
import org.example.model.Song;
import org.example.service.SongService;
import org.example.enums.Genre;
import org.example.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {

    @Autowired
    private final SongService songService;

    // =========================
    // GET SONG BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getSongById(@PathVariable("id") Long songId) {
        try {
            Song song = songService.getSongById(songId);
            return ResponseEntity.ok(song);
        } catch (ResourceNotFoundException  e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // =========================
    // GET SONG BY TITLE
    // =========================
    @GetMapping("/title/{title}")
    public ResponseEntity<?> getSongByTitle(@PathVariable String title) {
        try {
            Song song = songService.getSongByTitle(title);
            return ResponseEntity.ok(song);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // =========================
    // GET ALL SONGS
    // =========================
    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    // =========================
    // CREATE SONG
    // =========================
    @PostMapping("/create")
    public ResponseEntity<?> createSong(@RequestBody CreateSongRequest request) {
        Song song = songService.createSong(
                request.getTitle(),
                request.getArtistName(),
                request.getGenre(),
                request.getAlbumName(),
                request.getDuration()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(song);
    }


    // =========================
    // UPDATE SONG
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(
            @PathVariable("id") Long songId,
            @RequestBody Song song
    ) {
        try {
            Song updatedSong = songService.updateSong(songId, song);
            return ResponseEntity.ok(updatedSong);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // =========================
    // DELETE SONG
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable("id") Long songId) {
        try {
            songService.deleteSongById(songId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // =========================
    // GET ARTISTS BY SONG ID
    // =========================
    @GetMapping("/{id}/artists")
    public ResponseEntity<?> getArtistsBySongId(@PathVariable("id") Long songId) {
        try {
            Set<Artist> artists = songService.getArtistsBySongId(songId);
            return ResponseEntity.ok(artists);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // =========================
    // GET ALBUM BY SONG ID
    // =========================
    @GetMapping("/{id}/album")
    public ResponseEntity<?> getAlbumBySongId(@PathVariable("id") Long songId) {
        try {
            Album album = songService.getAlbumBySongId(songId);
            return ResponseEntity.ok(album);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
