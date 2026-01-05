package org.soundstream.service.song;

import org.soundstream.dto.request.CreateSongRequest;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.model.Album;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;

import java.util.List;
import java.util.Set;

public interface SongService {

    Song getSongById(Long songId);

    Song getSongByTitle(String title);

    List<Song> getAllSongs();

    SongResponseDTO createSong(CreateSongRequest request);

    Song updateSong(Long songId, Song song);

    void deleteSongById(Long songId);

    Set<Artist> getArtistsBySongId(Long songId);

    Album getAlbumBySongId(Long songId);
}
