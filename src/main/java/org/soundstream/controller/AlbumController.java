package org.soundstream.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soundstream.dto.request.CreateAlbumRequest;
import org.soundstream.dto.request.UpdateAlbumRequest;
import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.service.album.AlbumService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/{id}")
    public AlbumResponseDTO getAlbumById(
            @PathVariable Long id
    ) {
        return albumService.getAlbumById(id);
    }

    @GetMapping("/name/{name}")
    public AlbumResponseDTO getAlbumByName(
            @PathVariable String name
    ) {
        return albumService.getAlbumByName(name);
    }

    @GetMapping
    public Page<AlbumResponseDTO> getAllAlbums(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) {
        return albumService.getAllAlbums(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponseDTO createAlbum(
            @RequestBody @Valid CreateAlbumRequest request
    ) {
        return albumService.createAlbum(request);
    }

    @PutMapping("/{id}")
    public AlbumResponseDTO updateAlbum(
            @PathVariable Long albumId,
            @RequestBody UpdateAlbumRequest request
    ) {
        return albumService.updateAlbum(albumId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(
            @PathVariable Long id
    ) {
        albumService.deleteAlbumById(id);
    }

    @GetMapping("/{id}/songs")
    public Set<SongResponseDTO> getSongsByAlbumId(
            @PathVariable Long id
    ) {
        return albumService.getSongsByAlbumId(id);
    }
}
