package org.soundstream.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soundstream.dto.request.CreateArtistRequest;
import org.soundstream.dto.request.UpdateArtistRequest;
import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.service.artist.ArtistService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{id}")
    public ArtistResponseDTO getArtistById(
            @PathVariable Long id
    ) {
        return artistService.getArtistById(id);
    }

    @GetMapping("/name/{name}")
    public ArtistResponseDTO getArtistByName(
            @PathVariable String name
    ) {
        return artistService.getArtistByName(name);
    }

    @GetMapping
    public Page<ArtistResponseDTO> getAllArtists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return artistService.getAllArtists(page, size);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistResponseDTO createArtist(
            @RequestBody CreateArtistRequest request
    ) {
        return artistService.createArtist(request);
    }


    @PutMapping("/{id}")
    public ArtistResponseDTO updateArtist(
            @PathVariable Long id,
            @RequestBody UpdateArtistRequest request
    ) {
        return artistService.updateArtist(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(
            @PathVariable Long id
    ) {
        artistService.deleteArtistById(id);
    }

    @GetMapping("/{id}/songs")
    public Set<SongResponseDTO> getSongsByArtistId(
            @PathVariable Long id
    ) {
        return artistService.getSongsByArtistId(id);
    }
}
