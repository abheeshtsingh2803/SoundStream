package org.soundstream.service.song;

import org.soundstream.dto.request.CreateSongRequest;
import org.soundstream.dto.request.UpdateSongRequest;
import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.model.Album;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface SongService {

    SongResponseDTO getSongById(Long songId);

    SongResponseDTO getSongByTitle(String title);

    Page<SongResponseDTO> getAllSongs(int page, int size);

    SongResponseDTO createSong(CreateSongRequest request);

    SongResponseDTO updateSong(Long songId, UpdateSongRequest request);

    void deleteSongById(Long songId);

    Set<ArtistResponseDTO> getArtistsBySongId(Long songId);

    AlbumResponseDTO getAlbumBySongId(Long songId);
}
