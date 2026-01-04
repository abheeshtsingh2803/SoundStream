package org.soundstream.controller;

import lombok.RequiredArgsConstructor;
import org.soundstream.model.Album;
import org.soundstream.model.Song;
import org.soundstream.service.album.AlbumService;
import org.soundstream.exception.ResourceNotFoundException;
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
    public Album getAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id);
    }

    @GetMapping("/name/{name}")
    public Album getAlbumByName(@PathVariable String name) {
        return albumService.getAlbumByName(name);
    }

    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(
            @RequestParam String name,
            @RequestParam String artistName,
            @RequestParam int releaseYear
    ) {
        return albumService.createAlbum(name, artistName, releaseYear);
    }

    @PutMapping("/{id}")
    public Album updateAlbum(@PathVariable Long id, @RequestBody Album album) {
        return albumService.updateAlbum(id, album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbumById(id);
    }

    @GetMapping("/{id}/songs")
    public Set<Song> getSongsByAlbumId(@PathVariable Long id) {
        return albumService.getSongsByAlbumId(id);
    }
}
