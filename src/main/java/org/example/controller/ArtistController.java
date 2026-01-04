package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Artist;
import org.example.model.Song;
import org.example.service.ArtistService;
import org.example.enums.Genre;
import org.example.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(artistService.getArtistById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getArtistByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(artistService.getArtistByName(name));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createArtist(
            @RequestParam String name,
            @RequestParam Genre genre
    ) {
        try {
            Artist artist = artistService.createArtist(name, genre);
            return ResponseEntity.status(HttpStatus.CREATED).body(artist);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArtist(
            @PathVariable Long id,
            @RequestBody Artist artist
    ) {
        try {
            return ResponseEntity.ok(artistService.updateArtist(id, artist));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id) {
        try {
            artistService.deleteArtistById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<?> getSongsByArtistId(@PathVariable Long id) {
        try {
            Set<Song> songs = artistService.getSongsByArtistId(id);
            return ResponseEntity.ok(songs);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
