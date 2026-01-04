package org.soundstream.controller;

import lombok.RequiredArgsConstructor;
import org.soundstream.dto.CreateSongRequest;
import org.soundstream.enums.Genre;
import org.soundstream.model.Album;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.service.song.SongService;
import org.soundstream.exception.ResourceNotFoundException;
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

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        return songService.getSongById(id);
    }

    @GetMapping("/title/{title}")
    public Song getSongByTitle(@PathVariable String title) {
        return songService.getSongByTitle(title);
    }

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Song createSong(
            @RequestParam String title,
            @RequestParam String artistName,
            @RequestParam Genre genre,
            @RequestParam String albumName,
            @RequestParam int duration
    ) {
        return songService.createSong(title, artistName, genre, albumName, duration);
    }


    @PutMapping("/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song song) {
        return songService.updateSong(id, song);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable Long id) {
        songService.deleteSongById(id);
    }

    @GetMapping("/{id}/artists")
    public Set<Artist> getArtistsBySongId(@PathVariable Long id) {
        return songService.getArtistsBySongId(id);
    }

    @GetMapping("/{id}/album")
    public Album getAlbumBySongId(@PathVariable Long id) {
        return songService.getAlbumBySongId(id);
    }
}
