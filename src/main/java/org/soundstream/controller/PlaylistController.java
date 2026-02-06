package org.soundstream.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soundstream.dto.request.AddSongsToPlaylistRequest;
import org.soundstream.dto.request.CreatePlaylistRequest;
import org.soundstream.dto.request.UpdatePlaylistRequest;
import org.soundstream.dto.response.PlaylistResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.service.playlist.PlaylistService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/{id}")
    public PlaylistResponseDTO getPlaylistById(
            @PathVariable Long id
    ) {
        return playlistService.getPlaylistById(id);
    }

    @GetMapping("/name/{name}")
    public PlaylistResponseDTO getPlaylistByName(
            @PathVariable String name
    ) {
        return playlistService.getPlaylistByName(name);
    }

    @GetMapping
    public Page<PlaylistResponseDTO> getAllPlaylists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return playlistService.getAllPlaylists(page,size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistResponseDTO createPlaylist(
            @RequestBody @Valid CreatePlaylistRequest request
    ) {
        return playlistService.createPlaylist(request);
    }

    @PutMapping("/update/{id}")
    public PlaylistResponseDTO updatePlaylist(
            @PathVariable Long playlistId,
            @RequestBody UpdatePlaylistRequest request
    ) {
        return playlistService.updatePlaylist(playlistId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaylist(
            @PathVariable Long id
    ) {
        playlistService.deletePlaylistById(id);
    }

    @PostMapping("/{id}/songs")
    public PlaylistResponseDTO addSongsToPlaylist(
            @PathVariable Long id,
            @RequestBody @Valid AddSongsToPlaylistRequest request
    ) {
        return playlistService.addSongsToPlaylist(id, request.getSongIds());
    }


    @DeleteMapping("/{playlistId}/songs/{songId}")
    public PlaylistResponseDTO removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        return playlistService.removeSongFromPlaylist(playlistId, songId);
    }


    @GetMapping("/{playlistId}/songs")
    public Set<SongResponseDTO> getSongsByPlaylistId(
            @PathVariable Long playlistId
    ) {
        return playlistService.getSongsByPlaylistId(playlistId);
    }
}