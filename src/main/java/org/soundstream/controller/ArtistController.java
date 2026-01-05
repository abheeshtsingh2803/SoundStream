package org.soundstream.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soundstream.dto.request.CreateArtistRequest;
import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.service.artist.ArtistService;
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
    public Artist getArtistById(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @GetMapping("/name/{name}")
    public Artist getArtistByName(@PathVariable String name) {
        return artistService.getArtistByName(name);
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistResponseDTO createArtist(
            @RequestBody CreateArtistRequest request
    ) {
        return artistService.createArtist(request);
    }


    @PutMapping("/{id}")
    public Artist updateArtist(@PathVariable Long id, @RequestBody Artist artist) {
        return artistService.updateArtist(id, artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtistById(id);
    }

    @GetMapping("/{id}/songs")
    public Set<Song> getSongsByArtistId(@PathVariable Long id) {
        return artistService.getSongsByArtistId(id);
    }
}
