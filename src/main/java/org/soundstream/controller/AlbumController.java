package org.soundstream.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soundstream.dto.request.CreateAlbumRequest;
import org.soundstream.dto.response.AlbumResponseDTO;
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
    public AlbumResponseDTO createAlbum(@RequestBody @Valid CreateAlbumRequest request) {
        return albumService.createAlbum(request);
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
