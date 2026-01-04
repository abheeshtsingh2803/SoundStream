package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Album;
import org.example.model.Song;
import org.example.service.AlbumService;
import org.example.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(albumService.getAlbumById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getAlbumByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(albumService.getAlbumByName(name));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @PostMapping
    public ResponseEntity<?> createAlbum(
            @RequestParam String name,
            @RequestParam String artistName,
            @RequestParam int releaseYear
    ) {
        try {
            Album album = albumService.createAlbum(name, artistName, releaseYear);
            return ResponseEntity.status(HttpStatus.CREATED).body(album);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlbum(
            @PathVariable Long id,
            @RequestBody Album album
    ) {
        try {
            return ResponseEntity.ok(albumService.updateAlbum(id, album));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id) {
        try {
            albumService.deleteAlbumById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<?> getSongsByAlbumId(@PathVariable Long id) {
        try {
            Set<Song> songs = albumService.getSongsByAlbumId(id);
            return ResponseEntity.ok(songs);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
